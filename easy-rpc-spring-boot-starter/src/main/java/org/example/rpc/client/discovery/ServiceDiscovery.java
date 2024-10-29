package org.example.rpc.client.discovery;

import org.example.rpc.common.ServiceInterfaceInfo;

/**
 * 服务发现
 *
 * @Author Roc
 * @Date 2024/10/29 15:23
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名获取服务实例
     *
     * @param serviceName 服务名
     * @return 服务实例
     */
    ServiceInterfaceInfo selectOneInstance(String serviceName);
}
