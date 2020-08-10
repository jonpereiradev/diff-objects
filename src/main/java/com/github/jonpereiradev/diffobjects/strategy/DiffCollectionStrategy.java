package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.IndexComparator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


/**
 * Responsible for check the difference between two collections.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffCollectionStrategy implements DiffStrategy {

    /**
     * Check the difference between two objects for the diffMetadata configuration.
     *
     * @param expected object that is considered a state before the after object.
     * @param current object that is considered the before object updated.
     * @param metadata the diffMetadata that is mapped to make the instance.
     *
     * @return the instance result between the two objects.
     */
    @Override
    @SuppressWarnings("unchecked")
    public DiffResult diff(Object expected, Object current, DiffMetadata metadata) {
        DiffComparator fieldComparator = metadata.getComparator();
        Collection<?> beforeCollection = initializeCollection(expected, metadata.getMethod());
        Collection<?> afterCollection = initializeCollection(current, metadata.getMethod());

        if (beforeCollection == null && afterCollection == null) {
            return new DiffResult(null, null, true);
        }

        // if has not the same size, so they are different
        if (!isEqualsSize(beforeCollection, afterCollection)) {
            return new DiffResult(beforeCollection, afterCollection, false);
        }

        List<?> afterCopy = new ArrayList<>(afterCollection);
        Iterator<?> iterator = afterCollection.iterator();

        for (Object currentBefore : beforeCollection) {
            Object currentAfter = retrieveAfterObject(fieldComparator, afterCollection, iterator, currentBefore);

            // check the elements that exist on beforeState and not exists on afterState
            if (currentAfter == null) {
                return new DiffResult(beforeCollection, afterCollection, false);
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
                    return new DiffResult(beforeCollection, afterCollection, false);
                }
            } else if (!fieldComparator.equals(currentBefore, currentAfter)) {
                return new DiffResult(beforeCollection, afterCollection, false);
            }
        }

        // check the elements that exist on afterState and not exists on beforeState
        for (Object currentAfter : afterCopy) {
            boolean noneMatch = beforeCollection
                .stream()
                .noneMatch((o) -> fieldComparator.equals(o, currentAfter));

            if (noneMatch) {
                return new DiffResult(beforeCollection, afterCollection, false);
            }
        }

        return new DiffResult(beforeCollection, afterCollection, true);
    }

    /**
     * Find the after element to be compare.
     *
     * @param collection the comparator to get the after element.
     * @param afterCollection the collection to find the element.
     * @param iterator the iterator to find the next element.
     * @param currentBefore the current before to compare with comparator.
     *
     * @return the next after element to compare.
     */
    private Object retrieveAfterObject(
        DiffComparator collection,
        Collection<?> afterCollection,
        Iterator<?> iterator,
        Object currentBefore) {
        Object currentAfter = iterator.next();

        if (!(collection instanceof IndexComparator)) {
            Stream<?> stream = afterCollection.stream().filter((o) -> collection.equals(currentBefore, o));
            currentAfter = stream.findFirst().orElse(null);
        }
        return currentAfter;
    }

    /**
     * Initialize the collection with the value of method from object.
     *
     * @param object the object instance that has the method.
     * @param method the method from object that has the collection value.
     *
     * @return the collection value from object.
     */
    private Collection<?> initializeCollection(Object object, Method method) {
        Collection<?> collection = DiffReflections.invoke(object, method);

        if (collection != null && collection.isEmpty()) {
            return null;
        }

        return collection;
    }

    /**
     * Validates if the collection has the same size.
     *
     * @param beforeCollection the before collection from before object.
     * @param afterCollection the after collection from after object.
     *
     * @return {@code false} if the collection has not the same size.
     */
    private boolean isEqualsSize(Collection<?> beforeCollection, Collection<?> afterCollection) {
        return beforeCollection != null && afterCollection != null && beforeCollection.size() == afterCollection.size();
    }

    private DiffResult checkDiff(
        DiffMetadata diffMetadata,
        DiffComparator fieldComparator,
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
