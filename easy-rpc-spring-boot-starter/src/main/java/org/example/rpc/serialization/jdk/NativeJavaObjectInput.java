package org.example.rpc.serialization.jdk;

import org.example.rpc.common.utils.Assert;
import org.example.rpc.serialization.ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;

/**
 * Native java object input implementation
 *
 * @Author Roc
 * @Date 2024/11/12 17:45
 */
public class NativeJavaObjectInput implements ObjectInput {

    private final ObjectInputStream inputStream;

    public NativeJavaObjectInput(InputStream inputStream) throws IOException {
        this(new ObjectInputStream(inputStream));
    }

    public NativeJavaObjectInput(ObjectInputStream inputStream) {
        Assert.notNull(inputStream, "inputStream can not be null");
        this.inputStream = inputStream;
    }

    @Override
    public boolean readBool() throws IOException {
        return inputStream.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return inputStream.readByte();
    }

    @Override
    public short readShort() throws IOException {
        return inputStream.readShort();
    }

    @Override
    public int readInt() throws IOException {
        return inputStream.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return inputStream.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return inputStream.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return inputStream.readDouble();
    }

    @Override
    public String readUTF() throws IOException {
        return inputStream.readUTF();
    }

    @Override
    public byte[] readBytes() throws IOException {
        int len = inputStream.readInt();
        if (len < 0) {
            return null;
        } else if (len == 0) {
            return new byte[]{};
        } else {
            byte[] bytes = new byte[len];
            inputStream.readFully(bytes);
            return bytes;
        }
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    public ObjectInputStream getObjectInputStream() {
        return inputStream;
    }
}
