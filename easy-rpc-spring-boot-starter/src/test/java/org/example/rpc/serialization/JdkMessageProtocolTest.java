package org.example.rpc.serialization;

import org.apache.http.HttpStatus;
import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdkMessageProtocolTest {

    private JdkMessageProtocol protocol;
    private RpcRequest request;
    private RpcResponse response;

    @BeforeEach
    void setUp() {
        protocol = new JdkMessageProtocol();

        // Initialize a test request and response object
        request = new RpcRequest("org.example.rpc.example.provider.api.ExampleOneService", "testMethod", null, null);

        response = new RpcResponse();
        response.setStatus(String.valueOf(HttpStatus.SC_OK));
    }

    @Test
    void testMarshallingAndUnmarshallingRequest() throws Exception {
        // Serialize the RpcRequest
        byte[] data = protocol.marshallingReqMessage(request);
        assertNotNull(data);
        assertTrue(data.length > 0);

        // Deserialize the byte array back to RpcRequest
        RpcRequest deserializedRequest = protocol.unmarshallingReqMessage(data);
        assertNotNull(deserializedRequest);

        // Verify the deserialized request
        assertEquals(request.getServiceName(), deserializedRequest.getServiceName());
        assertEquals(request.getMethodName(), deserializedRequest.getMethodName());
    }

    @Test
    void testMarshallingAndUnmarshallingResponse() throws Exception {
        // Serialize the RpcResponse
        byte[] data = protocol.marshallingRespMessage(response);
        assertNotNull(data);
        assertTrue(data.length > 0);

        // Deserialize the byte array back to RpcResponse
        RpcResponse deserializedResponse = protocol.unmarshallingRespMessage(data);
        assertNotNull(deserializedResponse);

        // Verify the deserialized response
        assertEquals(response.getStatus(), deserializedResponse.getStatus());
    }

}