package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;


/**
 * Responsible for check the difference between two objects where value navigates into object properties.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffNestedStrategy implements DiffStrategy {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

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

        return new DiffResult(expectedObject, currentObject, comparator.equals(expectedObject, currentObject));
    }
}
