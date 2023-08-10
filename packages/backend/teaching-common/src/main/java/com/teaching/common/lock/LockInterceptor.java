package com.teaching.common.lock;

import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.RedisHelper;
import com.teaching.common.helper.SpringHelper;
import com.teaching.common.helper.StringHelper;
import javafx.util.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author sacher
 */
@Aspect
@Order(Integer.MIN_VALUE + 99)
public class LockInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LockInterceptor.class);

    private static final String PREFIX = "LOCK", SPLITTER = ":";

    private final RedisHelper redisHelper;

    public LockInterceptor(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Around("@annotation(com.teaching.common.lock.Lockable)")
    public Object onLock(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        Pair<String, Pair<String, Pair<Integer, String>>> lockP = tryLock(point, args);
        // 有锁则执行业务
        if (!StringHelper.isBlank(lockP.getKey())) {
            try {
                return point.proceed(args);
            } finally {
                redisHelper.unLock(lockP.getKey(), lockP.getValue().getKey());
            }
        }
        // 没锁抛异常
        else {
            throw new ServiceException(lockP.getValue().getValue().getKey(), lockP.getValue().getValue().getValue());
        }
    }

    private  Pair<String, Pair<String, Pair<Integer, String>>> tryLock(ProceedingJoinPoint point, Object[] args) {
        Object target = point.getTarget();
        Method method = joinPointMethod(point);
        Lockable lockable = method.getAnnotation(Lockable.class);
        try {
            String spVal = lockable.keyEL();
            if (!StringHelper.isBlank(spVal)) {
                spVal = SpringHelper.parseEL(spVal, target, method, args);
            }
            String lockKey = StringHelper.join(SPLITTER, PREFIX, lockable.name(), spVal);
            String lockVal = redisHelper.tryLock(lockKey, lockable.timeout(), lockable.duration());
            // 加锁成功
            if (!StringHelper.isBlank(lockVal)){
                return new Pair<>(lockKey, new Pair<>(lockVal, new Pair<>(lockable.code(),lockable.message())));
            }
            // 加锁失败
            else {
                return new Pair<>(StringHelper.EMPTY, new Pair<>(StringHelper.EMPTY, new Pair<>(lockable.code(),lockable.message())));
            }
        } catch (Exception e) {
            LOG.error(target.getClass().getName() + "." + method.getName() + " to try lock error ", e);
            return new Pair<>(StringHelper.EMPTY, new Pair<>(StringHelper.EMPTY, new Pair<>(lockable.code(),lockable.message())));
        }
    }

    private Method joinPointMethod(ProceedingJoinPoint point) {
        return ((MethodSignature) point.getSignature()).getMethod();
    }
}
