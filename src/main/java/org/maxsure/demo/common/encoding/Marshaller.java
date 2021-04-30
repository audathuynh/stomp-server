package org.maxsure.demo.common.encoding;

/**
 * The Interface Marshaller defines methods to marshal objects.
 *
 * @author Dat Huynh
 * @param <T> the generic type of the objects
 * @since 1.0
 */
public interface Marshaller<T> {

    /**
     * Marshals an object into bytes.
     *
     * @param object the object
     * @return the byte[]
     */
    byte[] marshal(T object);

}
