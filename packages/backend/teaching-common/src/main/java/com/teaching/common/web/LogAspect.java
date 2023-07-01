package com.teaching.common.web;

import com.alibaba.fastjson.JSONObject;
import com.teaching.common.core.IRequest;
import com.teaching.common.core.RequestContextSession;
import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.helper.StringHelper;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Objects;

/**
 * 请求校验
 *
 * @author sacher
 */
@Aspect
public class LogAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //参数
        Object[] args = joinPoint.getArgs();
        // 返回值
        Object proceed = null;
        // 异常
        Throwable e = null;
        try {
            verifyIRequest(args);
            // 执行该方法
            proceed = joinPoint.proceed(args);
        } catch (Throwable ex) {
            e = ex;
            throw ex;
        } finally {
            // 装载日志信息
            this.handleLog(joinPoint, proceed, e);
        }
        return proceed;
    }

    private void verifyIRequest(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof IRequest) {
                IRequest request = (IRequest) arg;
                request.verify();
            }
        }
    }

    private void handleLog(ProceedingJoinPoint joinPoint,Object proceed,Throwable e) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
        RequestContextSession session = RequestContextThreadHolder.get().getSession();
        if(Objects.nonNull(session)){
            session.operate = Objects.nonNull(apiOperation) ? apiOperation.value() : StringHelper.EMPTY;
            session.responseBody = JSONObject.toJSONString(proceed);
        }
    }

}
