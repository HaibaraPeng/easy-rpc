package org.example.rpc.server.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.MessageProtocol;
import org.example.rpc.serialization.ObjectInput;
import org.example.rpc.serialization.ObjectOutput;
import org.example.rpc.serialization.Serialization;
import org.example.rpc.server.registry.ServiceRegistry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

/**
 * @Author Roc
 * @Date 2024/11/4 15:37
 */
@Slf4j
public class RequestHandler {

    private final Serialization serialization;

    private final ServiceRegistry serviceRegistry;

    public RequestHandler(Serialization serialization, ServiceRegistry serviceRegistry) {
        this.serialization = serialization;
        this.serviceRegistry = serviceRegistry;
    }

    public byte[] handleRequest(byte[] data) throws Exception {
        ObjectInput deserialize = serialization.deserialize(new ByteArrayInputStream(data));
        RpcRequest rpcRequest = deserialize.readObject(RpcRequest.class);
        ServiceInterfaceInfo serviceInterfaceInfo = serviceRegistry.getRegisteredObj(rpcRequest.getServiceName());

        RpcResponse rpcResponse = new RpcResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput serialize = serialization.serialize(byteArrayOutputStream);
        if (serviceInterfaceInfo == null) {
            rpcResponse.setStatus(HttpStatus.SC_NOT_FOUND);
            serialize.writeObject(rpcResponse);
            serialize.flushBuffer();
            return byteArrayOutputStream.toByteArray();
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
        serialize.writeObject(rpcResponse);
        serialize.flushBuffer();
        return byteArrayOutputStream.toByteArray();
    }
}
