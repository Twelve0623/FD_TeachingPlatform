package com.teaching.common.helper;

import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RedisHelper {

    private static final Map<String, Pair<String, Integer>> LOCK_MAP = new ConcurrentHashMap<>();
    private static final long  LOCK_WAITE = 5L, RENEWAL = 2L;
    private static final String LUA_DEL_LOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String VAL_KEY = "val", TYPE_KEY = "vt", TIME_LUA = "local a=redis.call('TIME');return a[1]*1000000+a[2]";

    private RedisTemplate redisTemplate;

    private RedisTemplate gtsRedisTemplate;

    private RedisHelper(RedisTemplate redisTemplate, RedisTemplate gtsRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.gtsRedisTemplate = gtsRedisTemplate;
    }

    public static RedisHelper INSTANCE() {
        return new RedisHelper(SpringHelper.getBean("redisTemplate"),
                SpringHelper.getBean("gtsRedisTemplate"));
    }

    /** 加Redis锁，锁的最短时间为 5s **/
    public String tryLock(String lockKey, long timeout, int expire) {
        int exp = expire < LOCK_WAITE ? (int) LOCK_WAITE : expire;
        String lockVal = UUID.randomUUID().toString();
        long time = System.currentTimeMillis();
        do {
            String lock = lockKey + "^^" + lockVal;
            if (!LOCK_MAP.containsKey(lock) && this.setIfAbsent(lockKey, lockVal, exp)) {
                LOCK_MAP.put(lock, new Pair<>(lockKey, exp));
                return lockVal;
            }
            ThreadFactoryHelper.waiting(LOCK_WAITE, TimeUnit.MILLISECONDS);
        } while (System.currentTimeMillis() - time < timeout);
        return StringUtils.EMPTY;
    }

    /** 释放Redis锁 **/
    public boolean unLock(String lockKey, String lockVal) {
        LOCK_MAP.remove((lockKey + "^^" + lockVal));
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText(LUA_DEL_LOCK);
        return 1 == (long) redisTemplate.execute(defaultRedisScript, Lists.newArrayList(lockKey), Lists.newArrayList(lockVal));
    }

    /** 获取毫秒 **/
    public long timeGet() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText(TIME_LUA);
        Long timeLong = (Long) redisTemplate.execute(defaultRedisScript, Lists.newArrayList(), Lists.newArrayList());
        String time = timeLong.toString();
        if (!StringHelper.isBlank(time) && time.length() == 16 && StringHelper.isNumeric(time)) {
            return Long.parseLong(time.substring(0, time.length() -3));
        }
        return DateHelper.time();
    }

    /** 设置redis值 **/
    public boolean setIfAbsent(final String key, final String val, final int expire) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, val, expire, TimeUnit.SECONDS));
    }

    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }


    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setNoFormat(String key, Object value) {
        gtsRedisTemplate.opsForValue().set(key, value);
    }

    public void setNoFormat(String key, Object value, long time) {
        gtsRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public String getNoFormat(String key) {
        return (String) gtsRedisTemplate.opsForValue().get(key);
    }


    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }


    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }


    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }


    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    public Long incr(String key, long delta) {
        return gtsRedisTemplate.opsForValue().increment(key, delta);
    }


    public Long decr(String key, long delta) {
        return gtsRedisTemplate.opsForValue().increment(key, -delta);
    }

    private String serializableKey(String serializableId) {
        return "@objects:" + serializableId;
    }

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    public Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }


    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }


    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }


    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }


    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }


    public Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }


    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }


    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }


    public Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }


    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }


    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }


    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }


    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }


    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    public Long lPushAll(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }


    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Set<String> keysGet(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
