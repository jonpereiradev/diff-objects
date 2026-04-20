package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Strategy implementation responsible for checking the difference between two collections.
 * <p>
 * Compares two collections extracted from the provided objects using the configured metadata.
 * Handles element-wise comparison, size checks, and supports nested or single value diff strategies
 * based on the metadata configuration.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffCollectionStrategy implements DiffStrategy {

    /**
     * Compares two collections extracted from the given objects using the provided diff metadata.
     * <p>
     * The method checks for null or empty collections, compares collection sizes, and performs
     * element-wise comparison using the configured comparator or nested diff strategies.
     * Returns a {@link DiffResult} indicating whether the collections are considered equal.
     * </p>
     *
     * @param expected the object representing the state before the change
     * @param current the object representing the updated state after the change
     * @param metadata the diff metadata used to map and create the instance
     *
     * @return the result of comparing the two collections
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DiffResult diff(Object expected, Object current, DiffMetadata metadata) {
        DiffComparator fieldComparator = metadata.getComparator();
        Collection<?> beforeCollection = initializeCollection(expected, metadata.getMethod());
        Collection<?> afterCollection = initializeCollection(current, metadata.getMethod());
        Map<String, String> properties = metadata.getProperties();

        // Both null or empty: considered equal
        if (isNullOrEmpty(beforeCollection) && isNullOrEmpty(afterCollection)) {
            return DiffResult.forValue(null, null, true, properties);
        }

        // One is null/empty, the other is not: not equal
        if (isNullOrEmpty(beforeCollection) || isNullOrEmpty(afterCollection)) {
            return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
        }

        // Size mismatch: not equal
        if (beforeCollection.size() != afterCollection.size()) {
            return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
        }

        // For matching, use lists for index-based comparison, or fallback to set-based matching
        List<?> beforeList = (beforeCollection instanceof List) ? (List<?>) beforeCollection : new ArrayList<>(beforeCollection);
        List<?> afterList = (afterCollection instanceof List) ? (List<?>) afterCollection : new ArrayList<>(afterCollection);

        // Track matched indices in afterList
        boolean[] matched = new boolean[afterList.size()];

        outer:
        for (Object beforeElem : beforeList) {
            // Try to find a matching element in afterList
            for (int j = 0; j < afterList.size(); j++) {
                if (matched[j]) continue;
                Object afterElem = afterList.get(j);

                boolean equals;
                if (!metadata.getValue().isEmpty()) {
                    DiffResult single = checkDiff(metadata, fieldComparator, beforeElem, afterElem);
                    equals = single.isEquals();
                } else {
                    equals = fieldComparator.isEquals(beforeElem, afterElem);
                }

                if (equals) {
                    matched[j] = true;
                    continue outer;
                }
            }
            // No match found for beforeElem
            return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
        }

        // All elements matched
        return DiffResult.forValue(beforeCollection, afterCollection, true, properties);
    }

    /**
     * Initializes the collection by invoking the specified method on the given object.
     * Returns {@code null} if the object is {@code null} or the collection is null.
     *
     * @param object the object instance containing the collection
     * @param method the method to invoke to retrieve the collection
     *
     * @return the collection value from the object, or {@code null} if not present
     */
    private Collection<?> initializeCollection(Object object, Method method) {
        if (object == null || method == null) {
            return null;
        }

        return DiffReflections.invoke(object, method);
    }

    /**
     * Checks if the collection is null or empty.
     *
     * @param collection the collection to check
     *
     * @return true if null or empty, false otherwise
     */
    private boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Performs a nested or single value diff check between two elements using the provided metadata and comparator.
     *
     * @param diffMetadata the diff metadata describing the property to compare
     * @param fieldComparator the comparator to use for property-level equality
     * @param currentBefore the element from the expected collection
     * @param currentAfter the element from the current collection
     *
     * @return the {@link DiffResult} of the comparison
     */
    private DiffResult checkDiff(
        DiffMetadata diffMetadata,
        DiffComparator<?> fieldComparator,
        Object currentBefore,
        Object currentAfter) {

        String value = diffMetadata.getValue();
        String currentValue = value.contains(".") ? value.substring(0, value.indexOf(".")) : value;
        String nextValue = value.contains(".") ? value.substring(value.indexOf(".") + 1) : "";
        DiffStrategyType diffStrategyType = nextValue.isEmpty() ? DiffStrategyType.SINGLE : DiffStrategyType.NESTED;
        Method method = DiffReflections.discoverGetter(
            currentBefore != null ? currentBefore.getClass() : Object.class,
            currentValue
        );
        DiffMetadata metadata = new DiffMetadata(nextValue, method, diffStrategyType, fieldComparator);
        DiffStrategy strategy = metadata.getStrategy();

        return strategy.diff(currentBefore, currentAfter, metadata);
    }
}