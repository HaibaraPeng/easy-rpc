package org.example.rpc.server.network;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.*;
import org.example.rpc.serialization.jdk.JavaSerialization;
import org.example.rpc.server.registry.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestHandlerTest {

    private Serialization serialization;
    private ServiceRegistry serviceRegistry;
    private RequestHandler requestHandler;
    private ObjectInput objectInput;
    private ObjectOutput objectOutput;
    private RpcRequest rpcRequest;
    private ServiceInterfaceInfo serviceInterfaceInfo;

    @BeforeEach
    void setUp() throws IOException {
        serialization = mock(Serialization.class);
        serviceRegistry = mock(ServiceRegistry.class);
        requestHandler = new RequestHandler(serialization, serviceRegistry);

        objectInput = mock(ObjectInput.class);
        objectOutput = mock(ObjectOutput.class);
        rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("testService");
        rpcRequest.setMethodName("toUpperCase");

        serviceInterfaceInfo = mock(ServiceInterfaceInfo.class);

        when(serialization.deserialize(any())).thenReturn(objectInput);
        when(serialization.serialize(any())).thenReturn(objectOutput);
    }

    @Test
    public void testHandleRequest_ServiceNotFound() throws Exception {
        when(objectInput.readObject(RpcRequest.class)).thenReturn(rpcRequest);
        when(serviceRegistry.getRegisteredObj("testService")).thenReturn(null);

        byte[] response = requestHandler.handleRequest(new byte[0]);

        verify(objectOutput).writeObject(argThat(arg -> {
            RpcResponse rpcResponse = (RpcResponse) arg;
            return rpcResponse.getStatus() == HttpStatus.SC_NOT_FOUND;
        }));
        assertNotNull(response);
    }

    @Test
    public void testHandleRequest_SuccessfulInvocation() throws Exception {
        when(objectInput.readObject(RpcRequest.class)).thenReturn(rpcRequest);
        when(serviceRegistry.getRegisteredObj("testService")).thenReturn(serviceInterfaceInfo);

        when(serviceInterfaceInfo.getClazz()).thenReturn(String.class);
        when(serviceInterfaceInfo.getObj()).thenReturn("test");

        byte[] response = requestHandler.handleRequest(new byte[0]);

        verify(objectOutput).writeObject(argThat(arg -> {
            RpcResponse rpcResponse = (RpcResponse) arg;
            return rpcResponse.getStatus() == HttpStatus.SC_OK && rpcResponse.getData() != null;
        }));
        assertNotNull(response);
    }

    @Test
    public void testHandleRequest_ExceptionDuringInvocation() throws Exception {
        when(objectInput.readObject(RpcRequest.class)).thenReturn(rpcRequest);
        when(serviceRegistry.getRegisteredObj("testService")).thenReturn(serviceInterfaceInfo);

        when(serviceInterfaceInfo.getClazz()).thenReturn(String.class);
        when(serviceInterfaceInfo.getClazz().getMethod("toUpperCase", null)).thenThrow(new RuntimeException("some error"));

        byte[] response = requestHandler.handleRequest(new byte[0]);

        verify(objectOutput).writeObject(argThat(arg -> {
            RpcResponse rpcResponse = (RpcResponse) arg;
            return rpcResponse.getStatus() == HttpStatus.SC_INTERNAL_SERVER_ERROR && rpcResponse.getException() != null;
        }));
        assertNotNull(response);
    }

}