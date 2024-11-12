package org.example.rpc.serialization;

import java.io.IOException;

/**
 * @Author Roc
 * @Date 2024/11/11 17:46
 */
public interface ObjectOutput extends DataOutput {

    /**
     * write object.
     *
     * @param obj object.
     */
    void writeObject(Object obj) throws IOException;

    default void writeThrowable(Object obj) throws IOException {
        writeObject(obj);
    }
}
