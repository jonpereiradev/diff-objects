package com.github.jonpereiradev.diffobjects;

/**
 * Exception thrown for any generic error encountered during the diff execution.
 * <p>
 * This unchecked exception is used to signal problems that occur while performing
 * diff operations, such as reflection errors, invalid configuration, or unexpected failures.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public class DiffException extends RuntimeException {

    /**
     * Constructs a new {@code DiffException} with the specified detail message.
     *
     * @param message the detail message describing the error
     */
    public DiffException(String message) {
        super(message);
    }

}