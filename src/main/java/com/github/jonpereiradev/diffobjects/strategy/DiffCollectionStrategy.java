package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Responsible for checking the difference between two collections.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffCollectionStrategy implements DiffStrategy {

    /**
     * Checks the difference between two objects based on the diffMetadata configuration.
     *
     * @param expected the object that is considered the state before the {@code current} object
     * @param current the object that is considered the updated state after the {@code expected} object
     * @param metadata the diffMetadata used to map and create the instance
     *
     * @return the result of comparing the two objects
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

        // if it has not the same size, so they are different
        if (!isEqualsSize(beforeCollection, afterCollection)) {
            return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
        }

        List<?> afterCopy = new ArrayList<>(afterCollection);
        Iterator<?> iterator = afterCollection.iterator();

        for (Object currentBefore : beforeCollection) {
            Object currentAfter = iterator.next();

            // check the elements that exist on beforeState and not exists on afterState
            if (currentAfter == null) {
                return DiffResult.forValue(beforeCollection, afterCollection, false, properties);
            }

            afterCopy.remove(currentAfter);

            // check the elements that exist on both collections
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

        // check the elements that exist on afterState and not exists on beforeState
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
     * Initializes the collection with the value from the method of the object.
     *
     * @param object the object instance that contains the method
     * @param method the method of the object that provides the collection value
     *
     * @return the collection value from the object
     */
    private Collection<?> initializeCollection(Object object, Method method) {
        Collection<?> collection = DiffReflections.invoke(object, method);

        if (collection != null && collection.isEmpty()) {
            return null;
        }

        return collection;
    }

    /**
     * Validates if the collections have the same size.
     *
     * @param beforeCollection the collection from the before object
     * @param afterCollection the collection from the after object
     *
     * @return {@code false} if the collections do not have the same size
     */
    private boolean isEqualsSize(Collection<?> beforeCollection, Collection<?> afterCollection) {
        return beforeCollection != null && afterCollection != null && beforeCollection.size() == afterCollection.size();
    }

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
