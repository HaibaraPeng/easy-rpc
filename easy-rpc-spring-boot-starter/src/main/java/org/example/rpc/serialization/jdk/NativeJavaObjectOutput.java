package org.example.rpc.serialization.jdk;

import org.example.rpc.common.utils.Assert;
import org.example.rpc.serialization.ObjectOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Author Roc
 * @Date 2024/11/11 17:47
 */
public class NativeJavaObjectOutput implements ObjectOutput {

    private final ObjectOutputStream outputStream;

    public NativeJavaObjectOutput(ObjectOutputStream outputStream) {
        Assert.notNull(outputStream, "outputStream can not be null");
        this.outputStream = outputStream;
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        outputStream.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        outputStream.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        outputStream.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        outputStream.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        outputStream.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        outputStream.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        outputStream.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        outputStream.writeUTF(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            outputStream.writeInt(len);
            outputStream.write(v, off, len);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        outputStream.flush();
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    protected ObjectOutputStream getObjectOutputStream() {
        return outputStream;
    }
}
