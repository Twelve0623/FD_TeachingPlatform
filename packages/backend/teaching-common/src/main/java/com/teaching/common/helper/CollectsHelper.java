package com.teaching.common.helper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 集合类工具
 *
 * @author sacher
 * */
public final class CollectsHelper {
    private CollectsHelper() {
    }

    public static <T> boolean isNullOrEmpty(final Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNullOrEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    public static <K, V> boolean isNullOrEmpty(final Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> Optional<T> head(final List<T> list) {
        return isNullOrEmpty(list) ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    public static <T> Optional<T> end(final List<T> list) {
        return isNullOrEmpty(list) ? Optional.empty() : Optional.ofNullable(list.get(list.size() - 1));
    }

}
