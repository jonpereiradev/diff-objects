package com.github.jonpereiradev.diffobjects;

/**
 * Exception for any generic error found in the diff execution.
 *
 * @author jonpereiradev@gmail.com
 */
public class DiffException extends RuntimeException {

    public DiffException(String message) {
        super(message);
    }
}
