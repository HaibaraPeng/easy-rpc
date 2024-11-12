package org.example.rpc.serialization.jdk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/**
 * Compacted java object output implementation
 *
 * @Author Roc
 * @Date 2024/11/12 15:22
 */
public class CompactedObjectOutputStream extends ObjectOutputStream {
    public CompactedObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
        Class<?> clazz = desc.forClass();
        if (clazz.isPrimitive() || clazz.isArray()) {
            write(0);
            super.writeClassDescriptor(desc);
        } else {
            write(1);
            writeUTF(desc.getName());
        }
    }
}
