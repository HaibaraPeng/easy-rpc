package org.example.rpc.serialization.fastjson;

import com.alibaba.fastjson.JSON;
import org.example.rpc.serialization.ObjectInput;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @Author Roc
 * @Date 2024/11/16 14:43
 */
public class FastJsonObjectInput implements ObjectInput {

    private final BufferedReader reader;

    public FastJsonObjectInput(InputStream in) {
        this(new InputStreamReader(in));
    }

    public FastJsonObjectInput(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    @Override
    public boolean readBool() throws IOException {
        return read(boolean.class);
    }

    @Override
    public byte readByte() throws IOException {
        return read(byte.class);
    }

    @Override
    public short readShort() throws IOException {
        return read(short.class);
    }

    @Override
    public int readInt() throws IOException {
        return read(int.class);
    }

    @Override
    public long readLong() throws IOException {
        return read(long.class);
    }

    @Override
    public float readFloat() throws IOException {
        return read(float.class);
    }

    @Override
    public double readDouble() throws IOException {
        return read(double.class);
    }

    @Override
    public String readUTF() throws IOException {
        return read(String.class);
    }

    @Override
    public byte[] readBytes() throws IOException {
        return readLine().getBytes();
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        String json = readLine();
        return JSON.parse(json);
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return read(cls);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        String json = readLine();
        return (T) JSON.parseObject(json, type);
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        if (line == null || line.trim().isEmpty()) {
            throw new EOFException();
        }
        return line;
    }

    private <T> T read(Class<T> clazz) throws IOException {
        String json = readLine();
        return JSON.parseObject(json, clazz);
    }
}
