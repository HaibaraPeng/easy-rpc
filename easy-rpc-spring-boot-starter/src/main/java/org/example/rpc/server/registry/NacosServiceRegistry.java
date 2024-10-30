package org.example.rpc.server.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.config.RpcRegistryProperties;
import org.example.rpc.exception.RpcException;

/**
 * 通过Nacos提供的服务注册中心
 *
 * @Author Roc
 * @Date 2024/10/30 15:18
 */
@Slf4j
public class NacosServiceRegistry extends DefaultServiceRegistry {

    private final NamingService namingService;

    public NacosServiceRegistry(RpcRegistryProperties registryProperties) {
        try {
            namingService = NamingFactory.createNamingService(registryProperties.getAddress());
        } catch (NacosException e) {
            log.error("Nacos init error", e);
            throw new RpcException(e.getMessage());
        }
        log.info("Nacos server status: {}", namingService.getServerStatus());
    }

    @Override
    public void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception {
        super.register(serviceInterfaceInfo);
        // nacos注册当前实例
        namingService.registerInstance(serviceInterfaceInfo.getServiceName(), buildInstance(serviceInterfaceInfo));
    }

    private Instance buildInstance(ServiceInterfaceInfo serviceInterfaceInfo) {
        Instance instance = new Instance();
        instance.setIp(serviceInterfaceInfo.getIp());
        instance.setPort(serviceInterfaceInfo.getPort());
        return instance;
    }
}
