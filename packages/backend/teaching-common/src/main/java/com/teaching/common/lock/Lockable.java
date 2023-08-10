package com.teaching.common.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sacher
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lockable {
    /** 锁名 **/
    String name();
    /** 锁KEY表达式 单参数 #url 多参数 #url + '+' + #id  **/
    String keyEL() default "";
    /** 抢锁时间 **/
    long timeout() default 1000L;
    /** 锁持续时长 **/
    int duration() default 10;
    /** 错误提示消息  **/
    String message() default "";
    /** 错误提示CODE  **/
    int code() default 513;

}

