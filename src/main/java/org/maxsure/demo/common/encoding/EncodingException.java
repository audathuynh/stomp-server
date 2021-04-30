package org.maxsure.demo.common.encoding;

/**
 * The Class EncodingException defines exceptions when encoding data.
 *
 * @author Dat Huynh
 * @since 1.0
 */
public class EncodingException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new encoding exception.
     *
     * @param e the exception
     */
    public EncodingException(Exception e) {
        super(e);
    }

    /**
     * Instantiates a new encoding exception.
     *
     * @param message the error message
     */
    public EncodingException(String message) {
        super(message);
    }

}
