package com.teaching.common.helper;

import java.math.BigDecimal;

/**
 * 数字工具类
 * @author sacher
 * */
public final class MathHelper {

    private MathHelper() {
    }

    public static BigDecimal nvl(final BigDecimal num) {
        return num == null ? BigDecimal.ZERO : num;
    }

    public static long nvl(final Long num) {
        return num == null ? 0L : num;
    }

    public static int nvl(final Integer num) {
        return nvl(num, 0);
    }

    public static int nvl(final Integer num, Integer defaultVal) {
        return num == null ? defaultVal : num;
    }

    public static boolean nvl(final Boolean bool) {
        return null == bool ? Boolean.FALSE : bool;
    }

    public static long toLong(final String num, long defaultVal) {
        try{
            return StringHelper.isNumeric(num) ? Long.parseLong(num) : defaultVal;
        } catch (Exception e){
            return defaultVal;
        }
    }



}
