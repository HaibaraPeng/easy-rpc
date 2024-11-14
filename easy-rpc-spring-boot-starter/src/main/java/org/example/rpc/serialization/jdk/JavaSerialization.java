package org.example.rpc.serialization.jdk;

import org.example.rpc.serialization.ObjectInput;
import org.example.rpc.serialization.ObjectOutput;
import org.example.rpc.serialization.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.example.rpc.serialization.Constants.JAVA_SERIALIZATION_ID;

/**
 * @Author Roc
 * @Date 2024/11/11 17:53
 */
public class JavaSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return JAVA_SERIALIZATION_ID;
    }

    @Override
    public String getContentType() {
        return "x-application/java";
    }

    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new JavaObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new JavaObjectInput(input);
    }
}
