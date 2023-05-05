package com.teaching.common.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rits.cloning.Cloner;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * BEAN类工具
 *
 * @author sacher
 * */
public final class BeanHelper {
    private static final Logger LOG = LoggerFactory.getLogger(BeanHelper.class);

    private static final ConcurrentMap<Class<?>, ConcurrentMap<String, Field>> CF_MAP = Maps.newConcurrentMap();
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    private static final Cloner CLONER = new Cloner();

    private BeanHelper() {
    }

    static {
        MODEL_MAPPER.getConfiguration()
                   // 字段匹配
                   .setFieldMatchingEnabled(true)
                   // 类型匹配
                   .setFullTypeMatchingRequired(true)
                   // 严格策略
                   .setMatchingStrategy(MatchingStrategies.STRICT)
                   // 过滤NULL
                   .setPropertyCondition(ctx -> null != ctx.getSource())
                   .setProvider(request -> {
                       if (null!=request.getSource() && requireClone(request.getSource().getClass())) {
                           return CLONER.deepClone(request.getSource());
                       }
                       return null;
                   });
    }

    /**
     * 对象拷贝
     **/
    public static void copy(final Object source, final Object target) {
        if (null != source && null != target) {
            MODEL_MAPPER.map(source, target);
        }
    }

    /**
     * 对象克隆
     **/
    public static <Source, Target> Target castTo(final Source source, final Class<Target> destType) {
        if (null == source || null == destType) {
            return null;
        }
        try {
            Target target = destType.newInstance();
            copy(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("error to copy source as " + destType.getName(), e);
        }
    }

    /**
     * 集合对象克隆
     **/
    public static <Source, Target> List<Target> castTo(final Collection<Source> source, final Class<Target> targetType) {
        if (CollectsHelper.isNullOrEmpty(source)) {
            return Lists.newArrayListWithExpectedSize(0);
        }

        List<Target> result = Lists.newArrayListWithExpectedSize(source.size());
        for (Source src : source) {
            result.add(castTo(src, targetType));
        }
        return result;
    }

    /**
     * Bean 转 Map
     **/
    public static Map<String, Object> bean2Map(Object source) {
        return source instanceof JSONObject ? (JSONObject) source : (JSONObject) JSON.toJSON(source);
    }


    /**
     * 取对象字段值
     **/
    public static Object getProperty(final Object bean, final String name) {
        try {
            Field field = FieldUtils.getField(bean.getClass(), name, true);
            return field.get(bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查找对象中的字段
     **/
    public static Field findField(final Class<?> clz, final String fieldName) {
        ConcurrentMap<String, Field> fcMap = CF_MAP.get(clz);
        if (CollectsHelper.isNullOrEmpty(fcMap)) {
            fcMap = Maps.newConcurrentMap();
            return findField(clz, fieldName, fcMap);

        } else {
            Field field = fcMap.get(fieldName);
            if (null != field) {
                return field;
            }
            return findField(clz, fieldName, fcMap);
        }
    }

    /**
     * 判断是否基本类型
     **/
    public static boolean isPrimitiveType(final Class<?> clz) {
        return clz.isPrimitive()
                || isSubTypeOf(clz, Number.class, Boolean.class, Character.class, String.class, Date.class, LocalDate.class, LocalDateTime.class);
    }

    public static boolean isSubTypeOf(final Class targetClz, final Class... parentClz) {
        for (Class c : parentClz) {
            if (c.isAssignableFrom(targetClz)) {
                return true;
            }
        }
        return false;
    }

    private static Field findField(final Class<?> clz, final String fieldName, final ConcurrentMap<String, Field> fcMap) {
        Field field = null;
        try {
            field = clz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            LOG.trace("class: {} covert field {} error ", clz.getName(), fieldName, e);
            if (clz.getSuperclass() != null) {
                field = findField(clz.getSuperclass(), fieldName);
            }
        }
        if (null != field) {
            fcMap.put(fieldName, field);
            CF_MAP.put(clz, fcMap);
        }
        return field;
    }

    private static boolean requireClone(final Class<?> type) {
        return !isPrimitiveType(type);
    }
}
