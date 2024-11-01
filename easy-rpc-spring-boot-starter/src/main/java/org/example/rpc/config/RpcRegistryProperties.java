package org.example.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rpc 注册配置类
 *
 * @Author Roc
 * @Date 2024/10/29 15:54
 */
@ConfigurationProperties(prefix = "rpc.registry")
@Data
public class RpcRegistryProperties {
    private String type;
    private String address;
    private Integer exposePort = 6666;
}
