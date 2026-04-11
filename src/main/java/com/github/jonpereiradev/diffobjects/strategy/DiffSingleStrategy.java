package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Strategy implementation responsible for checking the difference between two simple objects
 * (without nested properties).
 * <p>
 * Uses the configured {@link DiffComparator} and reflection to extract property values from
 * the expected and current objects, then compares them for equality.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffSingleStrategy implements DiffStrategy {

    /**
     * Checks the difference between two objects using the metadata configuration.
     * <p>
     * Invokes the configured getter method on both the expected and current objects to retrieve
     * their property values, then compares these values using the provided comparator.
     * </p>
     *
     * @param expected the object representing the expected state
     * @param current the object representing the current state
     * @param metadata the metadata containing the configuration for the diff
     *
     * @return the diff result between the two objects
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DiffResult diff(Object expected, Object current, DiffMetadata metadata) {
        Object expectedValue = DiffReflections.invoke(expected, metadata.getMethod());
        Object currentValue = DiffReflections.invoke(current, metadata.getMethod());
        DiffComparator comparator = metadata.getComparator();
        boolean equals = comparator.isEquals(expectedValue, currentValue);

        return DiffResult.forValue(expectedValue, currentValue, equals, metadata.getProperties());
    }
}