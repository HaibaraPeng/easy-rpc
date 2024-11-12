package org.example.rpc.serialization.jdk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @Author Roc
 * @Date 2024/11/11 17:47
 */
public class JavaObjectOutput extends NativeJavaObjectOutput {

    public JavaObjectOutput(OutputStream outputStream) throws IOException {
        super(new ObjectOutputStream(outputStream));
    }

    public JavaObjectOutput(OutputStream outputStream, boolean compact) throws IOException {
        super(compact ? new CompactedObjectOutputStream(outputStream) : new ObjectOutputStream(outputStream));
    }

    @Override
    public void writeUTF(String v) throws IOException {
        if (v == null) {
            getObjectOutputStream().writeInt(-1);
        } else {
            getObjectOutputStream().writeInt(v.length());
            getObjectOutputStream().writeUTF(v);
        }
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        if (obj == null) {
            getObjectOutputStream().writeByte(0);
        } else {
            getObjectOutputStream().writeByte(1);
            getObjectOutputStream().writeObject(obj);
        }
    }
}
