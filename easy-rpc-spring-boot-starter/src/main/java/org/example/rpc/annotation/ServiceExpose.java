package org.example.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 服务提供者暴露服务接口
 *
 * @Author Roc
 * @Date 2024/10/30 16:22
 */
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceExpose {
}
