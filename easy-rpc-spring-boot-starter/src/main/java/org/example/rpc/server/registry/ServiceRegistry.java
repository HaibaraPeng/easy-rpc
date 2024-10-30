package org.example.rpc.server.registry;

import org.example.rpc.common.ServiceInterfaceInfo;

/**
 * 服务注册中心
 *
 * @Author Roc
 * @Date 2024/10/29 17:58
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     *
     * @param serviceInterfaceInfo 服务信息
     * @throws Exception
     */
    void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception;

    /**
     * 根据服务名获取服务信息
     *
     * @param serviceName
     * @return
     * @throws Exception
     */
    ServiceInterfaceInfo getRegisteredObj(String serviceName) throws Exception;
}
