package org.example.rpc.server.registry;

import org.example.rpc.common.ServiceInterfaceInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地服务注册器
 *
 * @Author Roc
 * @Date 2024/10/30 15:09
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private final Map<String, ServiceInterfaceInfo> localServiceMap = new ConcurrentHashMap<>();


    @Override
    public void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception {
        if (serviceInterfaceInfo == null) {
            throw new IllegalArgumentException("serviceInterfaceInfo can not be null");
        }
        localServiceMap.put(serviceInterfaceInfo.getServiceName(), serviceInterfaceInfo);
    }

    @Override
    public ServiceInterfaceInfo getRegisteredObj(String serviceName) throws Exception {
        return localServiceMap.get(serviceName);
    }
}
