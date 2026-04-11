package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;

/**
 * Strategy implementation responsible for checking the difference between two objects,
 * where the value navigates through nested properties of the objects.
 * <p>
 * This strategy traverses the property path specified in the {@link DiffMetadata} value,
 * invoking getter methods step-by-step to reach the nested property for comparison.
 * The comparison is performed using the configured {@link DiffComparator}.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffNestedStrategy implements DiffStrategy {

    /**
     * Regular expression used to split property paths (e.g., "address.city").
     */
    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

    /**
     * Compares two objects by navigating through their nested properties as specified in the metadata.
     * <p>
     * For each property in the path, the corresponding getter is invoked on both objects.
     * The final property values are compared using the configured comparator.
     * </p>
     *
     * @param expected the object representing the expected state
     * @param current the object representing the current state
     * @param metadata the metadata containing the configuration for the diff
     *
     * @return the {@link DiffResult} representing the difference between the two objects
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DiffResult diff(Object expected, Object current, DiffMetadata metadata) {
        DiffComparator comparator = metadata.getComparator();
        Method expectedMethod = metadata.getMethod();
        Method currentMethod = metadata.getMethod();
        Object expectedObject = DiffReflections.invoke(expected, expectedMethod);
        Object currentObject = DiffReflections.invoke(current, currentMethod);

        if (expectedObject != null || currentObject != null) {
            for (String property : metadata.getValue().split(REGEX_PROPERTY_SEPARATOR)) {
                if (expectedObject != null) {
                    expectedMethod = DiffReflections.discoverGetter(expectedObject.getClass(), property);
                    expectedObject = DiffReflections.invoke(expectedObject, expectedMethod);
                }

                if (currentObject != null) {
                    currentMethod = DiffReflections.discoverGetter(currentObject.getClass(), property);
                    currentObject = DiffReflections.invoke(currentObject, currentMethod);
                }
            }
        }

        boolean equals = comparator.isEquals(expectedObject, currentObject);
        return DiffResult.forValue(expectedObject, currentObject, equals, metadata.getProperties());
    }
}