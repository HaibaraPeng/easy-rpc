package org.example.rpc.client.network.netty;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.JdkMessageProtocol;
import org.example.rpc.serialization.MessageProtocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NettyRpcClientTest {

    private MessageProtocol protocol;
    private RpcRequest request;
    private ServiceInterfaceInfo serviceInterfaceInfo;

    @BeforeEach
    void setUp() {
        protocol = new JdkMessageProtocol();
        request = new RpcRequest("org.example.rpc.example.provider.api.ExampleOneService", "testOne",
                new Class[]{String.class}, new Object[]{"NettyRpcClientTest"});
        serviceInterfaceInfo = new ServiceInterfaceInfo("org.example.rpc.example.provider.api.ExampleOneService",
                "127.0.0.1", 8888);
    }

    @Test
    void sendMessage() throws Exception {
        byte[] data = new NettyRpcClient().sendMessage(protocol.marshallingReqMessage(request), serviceInterfaceInfo);
        assertNotNull(data);
        assertTrue(data.length > 0);

        RpcResponse response = protocol.unmarshallingRespMessage(data);
        assertNotNull(response);

        assertEquals(response.getStatus(), HttpStatus.SC_OK);
    }
}