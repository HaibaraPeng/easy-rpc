package org.example.rpc.serialization.fastjson;

import org.example.rpc.serialization.model.media.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author Roc
 * @Date 2024/11/18 16:24
 */
public class FastJsonObjectOutputTest {

    private FastJsonObjectOutput fastJsonObjectOutput;
    private FastJsonObjectInput fastJsonObjectInput;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @BeforeEach
    public void setUp() throws Exception {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.fastJsonObjectOutput = new FastJsonObjectOutput(byteArrayOutputStream);
    }

    @Test
    public void testWriteBool() throws IOException {
        this.fastJsonObjectOutput.writeBool(true);
        this.flushToInput();

        assertThat(fastJsonObjectInput.readBool(), is(true));
    }

    @Test
    public void testWriteShort() throws IOException {
        this.fastJsonObjectOutput.writeShort((short) 2);
        this.flushToInput();

        assertThat(fastJsonObjectInput.readShort(), is((short) 2));
    }

    @Test
    public void testWriteInt() throws IOException {
        this.fastJsonObjectOutput.writeInt(1);
        this.flushToInput();

        assertThat(fastJsonObjectInput.readInt(), is(1));
    }

    @Test
    public void testWriteLong() throws IOException {
        this.fastJsonObjectOutput.writeLong(1000L);
        this.flushToInput();

        assertThat(fastJsonObjectInput.readLong(), is(1000L));
    }

    @Test
    public void testWriteUTF() throws IOException {
        this.fastJsonObjectOutput.writeUTF("Pace Hasîtî 和平 Мир");
        this.flushToInput();

        assertThat(fastJsonObjectInput.readUTF(), is("Pace Hasîtî 和平 Мир"));
    }


    @Test
    public void testWriteFloat() throws IOException {
        this.fastJsonObjectOutput.writeFloat(1.88f);
        this.flushToInput();

        assertThat(this.fastJsonObjectInput.readFloat(), is(1.88f));
    }

    @Test
    public void testWriteDouble() throws IOException {
        this.fastJsonObjectOutput.writeDouble(1.66d);
        this.flushToInput();

        assertThat(this.fastJsonObjectInput.readDouble(), is(1.66d));
    }

    @Test
    public void testWriteBytes() throws IOException {
        this.fastJsonObjectOutput.writeBytes("hello".getBytes());
        this.flushToInput();

        assertThat(this.fastJsonObjectInput.readBytes(), is("hello".getBytes()));
    }

    @Test
    public void testWriteBytesWithSubLength() throws IOException {
        this.fastJsonObjectOutput.writeBytes("hello".getBytes(), 2, 2);
        this.flushToInput();

        assertThat(this.fastJsonObjectInput.readBytes(), is("ll".getBytes()));
    }

    @Test
    public void testWriteByte() throws IOException {
        this.fastJsonObjectOutput.writeByte((byte) 123);
        this.flushToInput();

        assertThat(this.fastJsonObjectInput.readByte(), is((byte) 123));
    }

    @Test
    public void testWriteObject() throws IOException, ClassNotFoundException {
        Image image = new Image("http://dubbo.apache.org/img/dubbo_white.png", "logo", 300, 480, Image.Size.SMALL);
        this.fastJsonObjectOutput.writeObject(image);
        this.flushToInput();

        Image readObjectForImage = fastJsonObjectInput.readObject(Image.class);
        assertThat(readObjectForImage, not(nullValue()));
        assertThat(readObjectForImage, is(image));
    }

    private void flushToInput() throws IOException {
        this.fastJsonObjectOutput.flushBuffer();
        this.byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        this.fastJsonObjectInput = new FastJsonObjectInput(byteArrayInputStream);
    }
}
