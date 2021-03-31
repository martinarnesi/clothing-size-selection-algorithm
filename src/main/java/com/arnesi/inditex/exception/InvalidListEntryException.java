package com.arnesi.inditex.exception;

public class InvalidListEntryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidListEntryException(String message) {
        super(message);
    }

    public InvalidListEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
