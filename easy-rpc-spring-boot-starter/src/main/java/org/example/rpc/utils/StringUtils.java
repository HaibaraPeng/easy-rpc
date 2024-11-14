package org.example.rpc.utils;

/**
 * @Author Roc
 * @Date 2024/11/14 11:50
 */
public abstract class StringUtils {

    private StringUtils() {
    }

    /**
     * is empty string.
     *
     * @param str source string.
     * @return is empty.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * is not empty string.
     *
     * @param str source string.
     * @return is not empty.
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
