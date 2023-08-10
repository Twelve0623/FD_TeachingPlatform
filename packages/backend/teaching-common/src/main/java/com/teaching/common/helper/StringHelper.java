package com.teaching.common.helper;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 **/
public final class StringHelper {
    private static final Pattern PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");
    public static String EMPTY = "";

    private StringHelper() {
    }

    public static String defaultString(final String src) {
        return isBlank(src) ? EMPTY : src;
    }

    public static String defaultIfBlank(final String src, final String defaultVal) {
        return isBlank(src) ? defaultVal : src;
    }

    public static String trimString(final String src) {
        return isBlank(src) ? EMPTY : src.trim();
    }

    public static String trimIfBlank(final String src, final String defaultVal) {
        return isBlank(src) ? defaultVal : src.trim();
    }

    public static boolean isBlank(final String src) {
        int strLen;
        if (src == null || (strLen = src.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(src.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isNumeric(String src) {
        return !isBlank(src) && PATTERN.matcher(src).matches();
    }

    public static List<String> list(String splitter, String src) {
        if (EMPTY.equals(splitter) && !isBlank(src)) {
            return Lists.newArrayList(src.split(splitter));
        }
        return Splitter.on(splitter).splitToList(defaultString(src));
    }

    /**
     * 集合转换成IBatis中的IN参数
     **/
    public static <T extends Serializable> String toIbatisIn(Collection<T> ids) {
        if (!CollectsHelper.isNullOrEmpty(ids)) {
            StringBuilder idsBuilder = new StringBuilder();
            // 过滤重复的ID
            LinkedHashSet<T> idSet = Sets.newLinkedHashSet(ids);
            for (T serial : idSet) {
                // 数字类型直接逗号分隔，字符串每个逗分隔都要带单引号
                if (serial instanceof Number) {
                    idsBuilder.append(",").append(serial);
                } else {
                    if (null != serial && !isBlank(serial.toString())) {
                        idsBuilder.append(",'").append(serial).append("'");
                    }
                }
            }
            if (idsBuilder.length() > 0) {
                return idsBuilder.replace(0, 1, "(").append(")").toString();
            }
        }
        return EMPTY;
    }

    public static <T> String join(String splitter, T ...src) {
        if(CollectsHelper.isNullOrEmpty(src)) {
            return EMPTY;
        }
        List<T> list = Lists.newArrayList();
        for (T t: src) {
            if (t instanceof Collection) {
                list.addAll((Collection)t);
            } else {
                list.add(t);
            }
        }
        return Joiner.on(splitter).join(list);
    }



}
