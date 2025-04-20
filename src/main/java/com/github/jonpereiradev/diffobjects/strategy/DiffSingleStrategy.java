package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;


/**
 * Responsible for checking the difference between two simple objects (without nested properties).
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffSingleStrategy implements DiffStrategy {

    /**
     * Checks the difference between two objects using the metadata configuration.
     *
     * @param expected the object representing the expected state.
     * @param current the object representing the current state.
     * @param metadata the metadata containing the configuration for the diff.
     *
     * @return the diff result between the two objects.
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
