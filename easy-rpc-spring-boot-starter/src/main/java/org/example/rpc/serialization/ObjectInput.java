package org.example.rpc.serialization;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author Roc
 * @Date 2024/11/11 17:46
 */
public interface ObjectInput extends DataInput {

    /**
     * read object
     *
     */
    Object readObject() throws IOException, ClassNotFoundException;

    /**
     * read object
     *
     * @param cls object class
     * @return object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if an ClassNotFoundException occurs
     */
    <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;


    default Throwable readThrowable() throws IOException, ClassNotFoundException {
        Object obj = readObject();
        if (!(obj instanceof Throwable)) {
            throw new IOException("Response data error, expect Throwable, but get " + obj);
        }
        return (Throwable) obj;
    }
}
