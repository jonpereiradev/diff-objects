package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;


/**
 * Responsible for check the difference between two simple objects (with no nested property).
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffSingleStrategy implements DiffStrategy {

    /**
     * Check the difference between two objects using the metadata configuration.
     *
     * @param expected object that represents the expected state of the object.
     * @param current object that represents the current state of the object.
     * @param metadata the metadata with the configuration for the diff.
     *
     * @return the diff result between the two objects.
     */
    @Override
    public DiffResult diff(Object expected, Object current, DiffMetadata metadata) {
        Object expectedValue = DiffReflections.invoke(expected, metadata.getMethod());
        Object currentValue = DiffReflections.invoke(current, metadata.getMethod());
        DiffComparator comparator = metadata.getComparator();

        return new DiffResult(expectedValue, currentValue, comparator.equals(expectedValue, currentValue));
    }
}
