package org.example.rpc.serialization.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.example.rpc.serialization.ObjectOutput;

import java.io.*;

/**
 * @Author Roc
 * @Date 2024/11/16 11:24
 */
public class FastJsonObjectOutput implements ObjectOutput {

    private final PrintWriter writer;

    public FastJsonObjectOutput(OutputStream out) {
        this(new OutputStreamWriter(out));
    }

    public FastJsonObjectOutput(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        writer.println(new String(v));
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        writer.println(new String(v, off, len));
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.write(obj);
        out.writeTo(writer);
        out.close();
        writer.println();
        writer.flush();
    }
}
