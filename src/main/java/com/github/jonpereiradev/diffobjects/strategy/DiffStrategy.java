package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;


/**
 * Defines a strategy to compare two objects and create the result for the diff.
 *
 * @author Jonathan Pereira
 * @see DiffSingleStrategy
 * @see DiffNestedStrategy
 * @see DiffCollectionStrategy
 * @since 1.0.0
 */
public interface DiffStrategy {

    /**
     * Check the difference between two objects using the metadata configuration.
     *
     * @param expected object that represents the expected state of the object.
     * @param current object that represents the current state of the object.
     * @param metadata the metadata with the configuration for the diff.
     *
     * @return the diff result between the two objects.
     */
    DiffResult diff(Object expected, Object current, DiffMetadata metadata);

}
