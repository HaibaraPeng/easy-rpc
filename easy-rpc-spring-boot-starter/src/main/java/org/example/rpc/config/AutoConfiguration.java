package org.example.rpc.config;

import com.alibaba.nacos.common.utils.StringUtils;
import org.example.rpc.client.discovery.NacosServiceDiscovery;
import org.example.rpc.client.discovery.RegisterEnums;
import org.example.rpc.client.discovery.ServiceDiscovery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot 自动注入
 *
 * @Author Roc
 * @Date 2024/10/29 15:57
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public RpcDiscoveryProperties rpcDiscoveryProperties() {
        return new RpcDiscoveryProperties();
    }

    @Bean
    public ServiceDiscovery serviceDiscovery(RpcDiscoveryProperties rpcDiscoveryProperties) {
        if (StringUtils.isBlank(rpcDiscoveryProperties.getRegister()) || rpcDiscoveryProperties.getRegister().equals(RegisterEnums.NACOS.name())) {
            return new NacosServiceDiscovery(rpcDiscoveryProperties);
        }
        return null;
    }
}
