package org.maxsure.demo.common.encoding.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.stream.StreamSource;
import org.maxsure.demo.common.encoding.Encoding;
import org.maxsure.demo.common.encoding.EncodingException;
import com.google.common.base.Preconditions;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * The Class JAXBEncoding provides an implementation of the interface
 * {@link org.maxsure.demo.common.encoding.Encoding} by using the library JAXB.
 *
 * @author Dat Huynh
 * @param <T> the generic type
 * @since 1.0
 */
public class JAXBEncoding<T> implements Encoding<T> {

    /** The jaxb context. */
    private final JAXBContext jaxbContext;

    /** The type of T. */
    private final Class<T> typeOfT;

    /**
     * Instantiates a new JAXB encoding.
     *
     * @param contextPath the context path
     * @param typeOfT the type of T
     */
    public JAXBEncoding(String contextPath, Class<T> typeOfT) {
        Preconditions.checkNotNull(contextPath, "contextPath");
        this.typeOfT = Preconditions.checkNotNull(typeOfT, "typeOfT");
        try {
            this.jaxbContext = JAXBContext.newInstance(contextPath);
        } catch (JAXBException e) {
            throw new EncodingException(e);
        }
    }

    /**
     * Marshals an object into an array of bytes.
     *
     * @param object the object
     * @return the byte array
     */
    @Override
    public byte[] marshal(T object) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(object, outputStream);
            return outputStream.toByteArray();
        } catch (JAXBException | IOException e) {
            throw new EncodingException(e);
        }
    }

    /**
     * Unmarshals an array of bytes into an object.
     *
     * @param bytes the array of bytes
     * @return the object
     */
    @Override
    public T unmarshal(byte[] bytes) {
        JAXBElement<T> rootElement;
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            rootElement = unmarshaller.unmarshal(new StreamSource(inputStream), typeOfT);
        } catch (JAXBException | IOException e) {
            throw new EncodingException(e);
        }
        if (rootElement.isNil()) {
            throw new EncodingException("Invalid XML document");
        }
        return rootElement.getValue();
    }

}
