package org.example.rpc.server.network;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.JdkMessageProtocol;
import org.example.rpc.serialization.MessageProtocol;
import org.example.rpc.server.registry.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestHandlerTest {

    @Mock
    private MessageProtocol mockMessageProtocol;
    @Mock
    private ServiceRegistry serviceRegistry;
    @Mock
    private ServiceInterfaceInfo serviceInterfaceInfo;

    private MessageProtocol messageProtocol;

    @BeforeEach
    void setUp() {
        messageProtocol = new JdkMessageProtocol();
    }

    @Test
    void handleRequest_ServiceNotFound() throws Exception {
        RequestHandler requestHandler = new RequestHandler(mockMessageProtocol, serviceRegistry);
        byte[] requestData = {};  // 输入请求数据
        RpcRequest rpcRequest = new RpcRequest("nonExistentService", "method", new Class[]{}, new Object[]{});
        RpcResponse expectedResponse = new RpcResponse();
        expectedResponse.setStatus(HttpStatus.SC_NOT_FOUND);

        when(mockMessageProtocol.unmarshallingReqMessage(requestData)).thenReturn(rpcRequest);
        when(serviceRegistry.getRegisteredObj("nonExistentService")).thenReturn(null);
        when(mockMessageProtocol.marshallingRespMessage(expectedResponse)).thenReturn("responseData".getBytes());

        byte[] result = requestHandler.handleRequest(requestData);

        assertEquals("responseData", new String(result));
    }

    @Test
    void handleRequest_MethodInvocationException() throws Exception {
        RequestHandler requestHandler = new RequestHandler(messageProtocol, serviceRegistry);
        RpcRequest rpcRequest = new RpcRequest("serviceName", "method", new Class[]{String.class}, new Object[]{"param"});

        when(serviceRegistry.getRegisteredObj("serviceName")).thenReturn(serviceInterfaceInfo);
        when(serviceInterfaceInfo.getClazz()).thenAnswer(invocation -> String.class);
        when(serviceInterfaceInfo.getClazz().getMethod("substring", int.class)).thenThrow(new RuntimeException("some error"));

        byte[] result = requestHandler.handleRequest(messageProtocol.marshallingReqMessage(rpcRequest));
        assertNotNull(result);
        assertTrue(result.length > 0);

        RpcResponse rpcResponse = messageProtocol.unmarshallingRespMessage(result);
        assertNotNull(rpcResponse);

        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, rpcResponse.getStatus());
    }
}