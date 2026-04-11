package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

/**
 * Defines a strategy to compare two objects and generate the diff result.
 * <p>
 * Implementations of this interface encapsulate the logic for comparing objects
 * according to specific rules or structures (e.g., single value, nested object, or collection).
 * The strategy uses the provided metadata to determine how the comparison should be performed.
 * </p>
 *
 * @author Jonathan Pereira
 * @see DiffSingleStrategy
 * @see DiffNestedStrategy
 * @see DiffCollectionStrategy
 * @since 1.0.0
 */
public interface DiffStrategy {

    /**
     * Compares the expected and current objects using the provided metadata configuration,
     * and produces a {@link DiffResult} describing the outcome of the comparison.
     *
     * @param expected the object representing the expected state
     * @param current the object representing the current state
     * @param metadata the metadata containing the configuration for the diff
     *
     * @return the {@link DiffResult} representing the difference between the two objects
     */
    DiffResult diff(Object expected, Object current, DiffMetadata metadata);

}