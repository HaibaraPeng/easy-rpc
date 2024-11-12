package org.example.rpc.common.utils;

/**
 * @Author Roc
 * @Date 2024/11/12 15:48
 */
public abstract class ClassUtils {

    protected ClassUtils() {
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            cl = clazz.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
        }
        return cl;
    }

    public static ClassLoader getClassLoader() {
        return getClassLoader(ClassUtils.class);
    }
}
