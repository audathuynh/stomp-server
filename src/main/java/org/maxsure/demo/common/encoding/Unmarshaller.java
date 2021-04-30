package org.maxsure.demo.common.encoding;

/**
 * The Interface Unmarshaller defines methods to unmarshal data into an object.
 *
 * @author Dat Huynh
 * @param <T> the generic type of the object
 * @since 1.0
 */
public interface Unmarshaller<T> {

    /**
     * Unmarshals an array of bytes into an object.
     *
     * @param bytes the array of bytes
     * @return the object
     */
    T unmarshal(byte[] bytes);

}
