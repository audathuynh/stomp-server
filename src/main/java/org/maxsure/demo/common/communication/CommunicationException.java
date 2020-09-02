package org.maxsure.demo.common.communication;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class CommunicationException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(String message, Exception ex) {
        super(message, ex);
    }

}
