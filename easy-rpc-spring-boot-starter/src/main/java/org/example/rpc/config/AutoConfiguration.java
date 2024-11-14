package org.example.rpc.config;

import com.alibaba.nacos.common.utils.StringUtils;
import org.example.rpc.client.ClientProxyFactory;
import org.example.rpc.client.discovery.NacosServiceDiscovery;
import org.example.rpc.client.network.netty.NettyRpcClient;
import org.example.rpc.common.RegisterTypeEnums;
import org.example.rpc.client.discovery.ServiceDiscovery;
import org.example.rpc.listener.DefaultRpcListener;
import org.example.rpc.serialization.jdk.JavaSerialization;
import org.example.rpc.server.network.RequestHandler;
import org.example.rpc.server.network.RpcServer;
import org.example.rpc.server.network.netty.NettyRpcServer;
import org.example.rpc.server.registry.NacosServiceRegistry;
import org.example.rpc.server.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (StringUtils.isBlank(rpcDiscoveryProperties.getType()) || rpcDiscoveryProperties.getType().equalsIgnoreCase(RegisterTypeEnums.NACOS.name())) {
            return new NacosServiceDiscovery(rpcDiscoveryProperties);
        }
        return null;
    }

    @Bean
    public RpcRegistryProperties rpcRegistryProperties() {
        return new RpcRegistryProperties();
    }

    @Bean
    public ServiceRegistry serviceRegistry(RpcRegistryProperties rpcRegistryProperties) {
        if (StringUtils.isBlank(rpcRegistryProperties.getType()) || rpcRegistryProperties.getType().equalsIgnoreCase(RegisterTypeEnums.NACOS.name())) {
            return new NacosServiceRegistry(rpcRegistryProperties);
        }
        return null;
    }

    @Bean
    public ClientProxyFactory clientProxyFactory(@Autowired ServiceDiscovery serviceDiscovery) {
        return new ClientProxyFactory(serviceDiscovery, new JavaSerialization(), new NettyRpcClient());
    }

    @Bean
    public DefaultRpcListener defaultRpcListener(@Autowired RpcRegistryProperties rpcRegistryProperties,
                                                 @Autowired ServiceRegistry serviceRegistry,
                                                 @Autowired ClientProxyFactory clientProxyFactory,
                                                 @Autowired RpcServer rpcServer) {
        return new DefaultRpcListener(rpcRegistryProperties, serviceRegistry, clientProxyFactory, rpcServer);
    }

    @Bean
    public RequestHandler requestHandler(@Autowired ServiceRegistry serviceRegistry) {
        return new RequestHandler(new JavaSerialization(), serviceRegistry);
    }

    @Bean
    public RpcServer rpcServer(@Autowired RpcRegistryProperties registryProperties,
                               @Autowired RequestHandler requestHandler) {
        return new NettyRpcServer(registryProperties.getExposePort(), requestHandler);
    }
}
