package org.example.rpc.client.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.config.RpcDiscoveryProperties;
import org.example.rpc.exception.RpcException;

/**
 * Nacos 服务发现
 *
 * @Author Roc
 * @Date 2024/10/29 15:53
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    private NamingService namingService;

    public NacosServiceDiscovery(RpcDiscoveryProperties properties) {
        try {
            namingService = NamingFactory.createNamingService(properties.getRegisterAddress());
        } catch (NacosException e) {
            log.error("Nacos client init error", e);
            throw new RpcException(e.getMessage());
        }
        log.info("Nacos server status: {}", namingService.getServerStatus());
    }

    @Override
    public ServiceInterfaceInfo selectOneInstance(String serviceName) {
        Instance instance;
        try {
            instance = namingService.selectOneHealthyInstance(serviceName);
        } catch (NacosException e) {
            log.error("Nacos client select one instance error", e);
            throw new RpcException(e.getMessage());
        }
        return new ServiceInterfaceInfo(serviceName, instance.getIp(), instance.getPort());
    }
}
