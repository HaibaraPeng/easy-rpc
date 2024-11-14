package org.example.rpc.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 序列化
 *
 * @Author Roc
 * @Date 2024/11/11 17:32
 */
public interface Serialization {

    /**
     * Get content type unique id, recommended that custom implementations use values different with
     * any value of {@link Constants} and don't greater than ExchangeCodec.SERIALIZATION_MASK (31)
     * because protocol use 5 bits to record serialization ID in header.
     *
     * @return content type id
     */
    byte getContentTypeId();

    /**
     * Get content type
     *
     * @return content type
     */
    String getContentType();

    /**
     * Get a serialization implementation instance
     *
     * @param output the underlying output stream
     * @return serializer
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * Get a deserialization implementation instance
     *
     * @param input the underlying input stream
     * @return deserializer
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;
}
