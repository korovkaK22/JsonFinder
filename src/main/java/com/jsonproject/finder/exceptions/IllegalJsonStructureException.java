package com.jsonproject.finder.exceptions;


/**
 * The IllegalJsonStructureException is a custom exception that signals an issue with the JSON file structure.
 * This exception is typically thrown when a JSON file does not conform to the expected structure,
 * such as when the JSON data is expected to be an array of objects but is not.
 */
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
