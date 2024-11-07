package org.example.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.client.discovery.ServiceDiscovery;
import org.example.rpc.client.network.RpcClient;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.exception.RpcException;
import org.example.rpc.serialization.MessageProtocol;

import java.lang.reflect.Proxy;

/**
 * 客户端代理工厂
 *
 * @Author Roc
 * @Date 2024/10/29 15:22
 */
@Slf4j
public class ClientProxyFactory {

    private ServiceDiscovery serviceDiscovery;
    private MessageProtocol messageProtocol;
    private RpcClient rpcClient;

    public ClientProxyFactory(ServiceDiscovery serviceDiscovery, MessageProtocol messageProtocol, RpcClient rpcClient) {
        this.serviceDiscovery = serviceDiscovery;
        this.messageProtocol = messageProtocol;
        this.rpcClient = rpcClient;
    }

    /**
     * 获取代理对象
     *
     * @param clazz 接口类
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxyInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, (proxy, method, args) -> {
            // 获取服务信息
            ServiceInterfaceInfo serviceInterfaceInfo = serviceDiscovery.selectOneInstance(clazz.getName());
            log.debug("select service:{}", serviceInterfaceInfo);
            if (serviceInterfaceInfo == null) {
                throw new RpcException("service not found");
            }

            // 封装RPC请求
            final RpcRequest request = new RpcRequest(serviceInterfaceInfo.getServiceName(), method.getName(), method.getParameterTypes(), args);

            // 序列化请求
            byte[] data = messageProtocol.marshallingReqMessage(request);

            // rpcClient发送消息
            byte[] byteResponse = rpcClient.sendMessage(data, serviceInterfaceInfo);

            // 解析response
            RpcResponse rpcResponse = messageProtocol.unmarshallingRespMessage(byteResponse);

            if (rpcResponse.getException() != null) {
                throw rpcResponse.getException();
            }

            return rpcResponse.getData();
        });
    }
}
