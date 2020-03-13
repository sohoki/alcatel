package com.sohoki.backoffice.alcatel.core.exception;

public class OTRestClientException extends Exception {
    private static final long serialVersionUID = -1224875719302690097L;

    public OTRestClientException(final String message) {
        super(message);
    }

    public OTRestClientException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
