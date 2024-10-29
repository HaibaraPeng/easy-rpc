package org.example.rpc.client.network;

import org.example.rpc.common.ServiceInterfaceInfo;

/**
 * Rpc客户端
 *
 * @Author Roc
 * @Date 2024/10/29 15:25
 */
public interface RpcClient {

    /**
     * 发送消息
     *
     * @param data
     * @param serviceInterfaceInfo
     * @return
     * @throws Exception
     */
    byte[] sendMessage(byte[] data, ServiceInterfaceInfo serviceInterfaceInfo) throws Exception;
}
