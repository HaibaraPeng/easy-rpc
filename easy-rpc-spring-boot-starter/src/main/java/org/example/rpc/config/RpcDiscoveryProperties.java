package org.example.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rpc 配置类
 *
 * @Author Roc
 * @Date 2024/10/29 15:54
 */
@ConfigurationProperties(prefix = "rpc.discovery")
@Data
public class RpcDiscoveryProperties {
    private String register;
    private String registerAddress;
}
