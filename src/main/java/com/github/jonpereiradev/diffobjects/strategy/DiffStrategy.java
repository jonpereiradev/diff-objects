package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;


/**
 * Defines a strategy to compare two objects and generate the diff result.
 *
 * @author Jonathan Pereira
 * @see DiffSingleStrategy
 * @see DiffNestedStrategy
 * @see DiffCollectionStrategy
 * @since 1.0.0
 */
public interface DiffStrategy {

    /**
     * Checks the difference between two objects using the metadata configuration.
     *
     * @param expected the object representing the expected state.
     * @param current the object representing the current state.
     * @param metadata the metadata containing the configuration for the diff.
     *
     * @return the diff result between the two objects.
     */
    DiffResult diff(Object expected, Object current, DiffMetadata metadata);

}
