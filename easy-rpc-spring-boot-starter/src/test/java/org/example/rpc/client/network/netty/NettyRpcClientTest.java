package org.example.rpc.client.network.netty;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.serialization.ObjectOutput;
import org.example.rpc.serialization.Serialization;
import org.example.rpc.serialization.jdk.JavaSerialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class NettyRpcClientTest {

    private Serialization serialization;
    private RpcRequest request;
    private ServiceInterfaceInfo serviceInterfaceInfo;

    @BeforeEach
    void setUp() {
        serialization = new JavaSerialization();
        request = new RpcRequest("org.example.rpc.example.provider.api.ExampleOneService", "testOne",
                new Class[]{String.class}, new Object[]{"NettyRpcClientTest"});
        serviceInterfaceInfo = new ServiceInterfaceInfo("org.example.rpc.example.provider.api.ExampleOneService",
                "127.0.0.1", 8888);
    }

    @Test
    void sendMessage() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutput serialize = serialization.serialize(out);
        serialize.writeObject(request);
        serialize.flushBuffer();

        byte[] data = new NettyRpcClient().sendMessage(out.toByteArray(), serviceInterfaceInfo);
        assertNotNull(data);
        assertTrue(data.length > 0);

        RpcResponse response = serialization.deserialize(new ByteArrayInputStream(data)).readObject(RpcResponse.class);
        assertNotNull(response);

        assertEquals(response.getStatus(), HttpStatus.SC_OK);
    }
}