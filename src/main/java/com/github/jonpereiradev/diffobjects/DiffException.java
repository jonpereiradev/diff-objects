package com.github.jonpereiradev.diffobjects;


/**
 * Exception for any generic error found in the diff execution.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public class DiffException extends RuntimeException {

    public DiffException(String message) {
        super(message);
    }

}
