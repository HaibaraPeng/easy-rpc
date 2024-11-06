package org.example.rpc.server.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.MessageProtocol;
import org.example.rpc.server.registry.ServiceRegistry;

import java.lang.reflect.Method;

/**
 * @Author Roc
 * @Date 2024/11/4 15:37
 */
@Slf4j
public class RequestHandler {

    private final MessageProtocol messageProtocol;

    private final ServiceRegistry serviceRegistry;

    public RequestHandler(MessageProtocol messageProtocol, ServiceRegistry serviceRegistry) {
        this.messageProtocol = messageProtocol;
        this.serviceRegistry = serviceRegistry;
    }

    public byte[] handleRequest(byte[] data) throws Exception {
        RpcRequest rpcRequest = messageProtocol.unmarshallingReqMessage(data);
        ServiceInterfaceInfo serviceInterfaceInfo = serviceRegistry.getRegisteredObj(rpcRequest.getServiceName());

        RpcResponse rpcResponse = new RpcResponse();
        if (serviceInterfaceInfo == null) {
            rpcResponse.setStatus(HttpStatus.SC_NOT_FOUND);
            return messageProtocol.marshallingRespMessage(rpcResponse);
        }

        try {
            final Method method = serviceInterfaceInfo.getClazz().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            final Object ret = method.invoke(serviceInterfaceInfo.getObj(), rpcRequest.getParameters());
            rpcResponse.setStatus(HttpStatus.SC_OK);
            rpcResponse.setData(ret);
        } catch (Exception e) {
            log.warn("invoke method error: {}", e.getMessage());
            rpcResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            rpcResponse.setException(e);
        }
        return messageProtocol.marshallingRespMessage(rpcResponse);
    }
}
