package org.example.rpc.serialization.fastjson;

import org.example.rpc.serialization.ObjectInput;
import org.example.rpc.serialization.ObjectOutput;
import org.example.rpc.serialization.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.example.rpc.serialization.Constants.FASTJSON_SERIALIZATION_ID;

/**
 * @Author Roc
 * @Date 2024/11/15 17:42
 */
public class FastJsonSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return FASTJSON_SERIALIZATION_ID;
    }

    @Override
    public String getContentType() {
        return "text/json";
    }

    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new FastJsonObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new FastJsonObjectInput(input);
    }
}
