package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

/**
 * Defines a strategy to compare two objects and create the result of the diff.
 *
 * @author jonpereiradev@gmail.com
 *
 * @see DiffSingleStrategy
 * @see DiffDeepStrategy
 * @see DiffCollectionStrategy
 */
public interface DiffStrategy {

    /**
     * Check the difference between two objects for the metadata configuration.
     *
     * @param before object that is considered a state before the after object.
     * @param after object that is considered the before object updated.
     * @param diffMetadata the metadata with the configuration for the diff.
     *
     * @return the diff result between the two objects.
     */
    DiffResult diff(Object before, Object after, DiffMetadata diffMetadata);

}
