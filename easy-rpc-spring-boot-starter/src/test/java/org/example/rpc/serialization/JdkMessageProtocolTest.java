package org.example.rpc.serialization;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.example.rpc.serialization.jdk.JavaSerialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@Deprecated
class JdkMessageProtocolTest {

    private Serialization serialization;
    private RpcRequest request;
    private RpcResponse response;

    @BeforeEach
    void setUp() {
        serialization = new JavaSerialization();

        // Initialize a test request and response object
        request = new RpcRequest("org.example.rpc.example.provider.api.ExampleOneService", "testMethod", null, null);

        response = new RpcResponse();
        response.setStatus(HttpStatus.SC_OK);
    }

    @Test
    void testMarshallingAndUnmarshallingRequest() throws Exception {
        // Serialize the RpcRequest
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput serialize = serialization.serialize(outputStream);
        serialize.writeObject(request);
        serialize.flushBuffer();
        byte[] data = outputStream.toByteArray();
        assertNotNull(data);
        assertTrue(data.length > 0);

        // Deserialize the byte array back to RpcRequest
        RpcRequest deserializedRequest = serialization.deserialize(new ByteArrayInputStream(data)).readObject(RpcRequest.class);
        assertNotNull(deserializedRequest);

        // Verify the deserialized request
        assertEquals(request.getServiceName(), deserializedRequest.getServiceName());
        assertEquals(request.getMethodName(), deserializedRequest.getMethodName());
    }

    @Test
    void testMarshallingAndUnmarshallingResponse() throws Exception {
        // Serialize the RpcResponse
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput serialize = serialization.serialize(outputStream);
        serialize.writeObject(response);
        serialize.flushBuffer();
        byte[] data = outputStream.toByteArray();
        assertNotNull(data);
        assertTrue(data.length > 0);

        // Deserialize the byte array back to RpcResponse
        RpcResponse deserializedResponse = serialization.deserialize(new ByteArrayInputStream(data)).readObject(RpcResponse.class);
        assertNotNull(deserializedResponse);

        // Verify the deserialized response
        assertEquals(response.getStatus(), deserializedResponse.getStatus());
    }

}