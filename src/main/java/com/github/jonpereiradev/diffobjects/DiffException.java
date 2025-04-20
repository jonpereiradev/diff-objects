package com.github.jonpereiradev.diffobjects;


/**
 * Exception thrown for any generic error encountered during the diff execution.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public class DiffException extends RuntimeException {

    public DiffException(String message) {
        super(message);
    }

}
