package org.example.rpc.serialization.fastjson;

import org.example.rpc.serialization.AbstractSerializationTest;
import org.example.rpc.serialization.ObjectInput;
import org.example.rpc.serialization.ObjectOutput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @Author Roc
 * @Date 2024/11/16 14:59
 */
public class FastJsonSerializationTest {

    private FastJsonSerialization fastJsonSerialization;

    @BeforeEach
    public void setUp() {
        this.fastJsonSerialization = new FastJsonSerialization();
    }

    @Test
    public void testContentType() {
        assertThat(fastJsonSerialization.getContentType(), is("text/json"));
    }

    @Test
    public void testContentTypeId() {
        assertThat(fastJsonSerialization.getContentTypeId(), is((byte) 6));
    }

    @Test
    public void testObjectOutput() throws IOException {
        ObjectOutput objectOutput = fastJsonSerialization.serialize(mock(OutputStream.class));
        assertThat(objectOutput, Matchers.<ObjectOutput>instanceOf(FastJsonObjectOutput.class));
    }

    @Test
    public void testObjectInput() throws IOException {
        ObjectInput objectInput = fastJsonSerialization.deserialize(mock(InputStream.class));
        assertThat(objectInput, Matchers.<ObjectInput>instanceOf(FastJsonObjectInput.class));
    }

}
