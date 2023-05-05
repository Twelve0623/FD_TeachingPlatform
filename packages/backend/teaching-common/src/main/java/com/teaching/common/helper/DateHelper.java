package com.teaching.common.helper;

import com.teaching.common.core.DateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author sacher
 * */
public final class DateHelper {
    public static Date MIN = ofDate(0L);
    public static final long SECOND_TIME = 1000L;
    public static final long MINUTE_TIME = 60 * SECOND_TIME;
    public static final long HOUR_TIME = 60 * MINUTE_TIME;
    public static final long DAY_TIME = 24 * HOUR_TIME;

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    private DateHelper() {
    }


    /**
     * 给定 format 将 Date 转换成 string
     **/
    public static String format(final Date date, final DateFormat format) {
        return null == date ? format.name() : new SimpleDateFormat(format.name()).format(date);
    }


    /**
     * 给定 format 将 string 转换成 Date
     **/
    public static Date ofDate(String sDate, DateFormat format) {
        try {
            return new SimpleDateFormat(format.name()).parse(sDate);
        } catch (Exception e) {
            throw new RuntimeException("ofDate error, sDate: " + sDate + " format: " + format.name(), e);
        }
    }

    /**
     * 将秒时间戳 转换成 Date
     **/
    public static Date ofDate(long time) {
        return Date.from(Instant.ofEpochMilli(time));
    }

    /**
     * 当前系统时间 Date 类型
     **/
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 给定 format 将当前系统时间转换成 string
     **/
    public static String now(DateFormat format) {
        return format(now(), format);
    }

    /**
     * 当前系统时间秒
     **/
    public static long second() {
        return Instant.now().getEpochSecond();
    }

    /**
     * Date 时间秒
     **/
    public static long second(final Date date) {
        return null == date ? 0L : date.getTime() / 1000;
    }

    /**
     * 当前系统时间毫秒
     **/
    public static long time() {
        return System.currentTimeMillis();
    }


}
