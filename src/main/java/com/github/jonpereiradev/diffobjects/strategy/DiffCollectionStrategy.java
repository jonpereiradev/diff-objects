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

        if (beforeCollection == null && afterCollection == null) {
            return DiffResult.forValue(null, null, true, properties);
        }

        // If collections do not have the same size, they are different
        if (!isEqualsSize(beforeCollection, afterCollection)) {
            return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
        }

        List<?> afterCopy = new ArrayList<>(afterCollection);
        Iterator<?> iterator = afterCollection.iterator();

        for (Object currentBefore : beforeCollection) {
            Object currentAfter = iterator.next();

            // Check elements present in beforeCollection but not in afterCollection
            if (currentAfter == null) {
                return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
            }

            afterCopy.remove(currentAfter);

            // Check elements present in both collections
            if (!metadata.getValue().isEmpty()) {
                DiffResult single = checkDiff(
                    metadata,
                    fieldComparator,
                    currentBefore,
                    currentAfter
                );

                if (!single.isEquals()) {
                    return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
                }
            } else if (!fieldComparator.isEquals(currentBefore, currentAfter)) {
                return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
            }
        }

        // Check elements present in afterCollection but not in beforeCollection
        for (Object currentAfter : afterCopy) {
            boolean noneMatch = beforeCollection
                .stream()
                .noneMatch((o) -> fieldComparator.isEquals(o, currentAfter));

            if (noneMatch) {
                return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
            }
        }

        return DiffResult.forValue(beforeCollection, afterCollection, true, properties);
    }

    /**
     * Initializes the collection by invoking the specified method on the given object.
     * Returns {@code null} if the collection is empty or the object is {@code null}.
     *
     * @param object the object instance containing the collection
     * @param method the method to invoke to retrieve the collection
     *
     * @return the collection value from the object, or {@code null} if empty or not present
     */
    private Collection<?> initializeCollection(Object object, Method method) {
        Collection<?> collection = DiffReflections.invoke(object, method);

        if (collection != null && collection.isEmpty()) {
            return null;
        }

        return collection;
    }

    /**
     * Checks if both collections are non-null and have the same size.
     *
     * @param beforeCollection the collection from the expected object
     * @param afterCollection the collection from the current object
     *
     * @return {@code true} if both collections are non-null and have the same size; {@code false} otherwise
     */
    private boolean isEqualsSize(Collection<?> beforeCollection, Collection<?> afterCollection) {
        return beforeCollection != null && afterCollection != null && beforeCollection.size() == afterCollection.size();
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
        Method method = DiffReflections.discoverGetter(currentBefore.getClass(), currentValue);
        DiffMetadata metadata = new DiffMetadata(nextValue, method, diffStrategyType, fieldComparator);
        DiffStrategy strategy = metadata.getStrategy();

        return strategy.diff(currentBefore, currentAfter, metadata);
    }
}