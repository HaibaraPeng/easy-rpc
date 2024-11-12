package org.example.rpc.common.utils;

/**
 * @Author Roc
 * @Date 2024/11/12 14:47
 */
public abstract class Assert {

    protected Assert() {
    }

    public static void notNull(Object object, RuntimeException exception) {
        if (object == null) {
            throw exception;
        }
    }

    public static void notNull(Object object, String message) {
        notNull(object, new IllegalArgumentException(message));
    }

}
