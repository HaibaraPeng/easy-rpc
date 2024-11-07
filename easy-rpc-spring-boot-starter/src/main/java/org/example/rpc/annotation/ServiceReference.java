package org.example.rpc.annotation;

import java.lang.annotation.*;

/**
 * 接入服务端注解
 *
 * @Author Roc
 * @Date 2024/11/1 15:46
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceReference {
}
