package org.example.rpc.common.utils;

import java.util.Map;

/**
 * @Author Roc
 * @Date 2024/11/14 15:08
 */
public abstract class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmptyMap(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * Return {@code true} if the supplied Map is {@code not null} or not empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is not empty
     */
    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }
}
