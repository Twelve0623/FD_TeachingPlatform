package com.teaching.platform.config;

import com.teaching.common.helper.RedisHelper;
import com.teaching.common.lock.LockInterceptor;
import com.teaching.common.web.IWebFilter;
import com.teaching.common.web.WebMvcConfig;
import com.teaching.platform.core.LoginCodeProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 16:54
 **/
@Configuration
public class ApplicationConfig {

    @Configuration
    public class WebConfig extends WebMvcConfig {
        @Bean
        protected IWebFilter configAuthFilter() {
            return new WebAuthFilter();
        }
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置hashKey和hashValue的序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置支持事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "gtsRedisTemplate")
    public RedisTemplate getGtsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
        // 设置hashKey和hashValue的序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
        // 设置支持事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    @Bean
    public RedisHelper initRedisHelper() {
        return RedisHelper.INSTANCE();
    }
//
//    @Bean
//    protected LockInterceptor lockInterceptor(@Autowired RedisHelper redisHelper) {
//        return new LockInterceptor(redisHelper);
//    }


    @Bean
    @ConfigurationProperties(prefix = "login", ignoreUnknownFields = true)
    public LoginCodeProperties loginCodeProperties() {
        return new LoginCodeProperties();
    }
}
