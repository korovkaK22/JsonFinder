package com.jsonproject.finder.exceptions;

public class IllegalJsonStructureException extends Exception{
    public IllegalJsonStructureException() {
    }

    public IllegalJsonStructureException(String message) {
        super(message);
    }

    public IllegalJsonStructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalJsonStructureException(Throwable cause) {
        super(cause);
    }

    public IllegalJsonStructureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
