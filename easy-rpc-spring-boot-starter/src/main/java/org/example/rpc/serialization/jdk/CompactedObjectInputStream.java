package org.example.rpc.serialization.jdk;

import org.example.rpc.common.utils.ClassUtils;

import java.io.*;

/**
 * Compacted java object input implementation
 *
 * @Author Roc
 * @Date 2024/11/12 15:45
 */
public class CompactedObjectInputStream extends ObjectInputStream {

    private final ClassLoader classLoader;

    public CompactedObjectInputStream(InputStream in) throws IOException {
        this(in, Thread.currentThread().getContextClassLoader());
    }

    public CompactedObjectInputStream(InputStream in, ClassLoader cl) throws IOException {
        super(in);
        classLoader = cl == null ? ClassUtils.getClassLoader() : cl;
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        int type = read();
        if (type < 0) {
            throw new EOFException();
        }
        switch (type) {
            case 0:
                return super.readClassDescriptor();
            case 1:
                Class<?> clazz = classLoader.loadClass(readUTF());
                return ObjectStreamClass.lookup(clazz);
            default:
                throw new StreamCorruptedException("Unexpected class descriptor type: " + type);
        }
    }


}
