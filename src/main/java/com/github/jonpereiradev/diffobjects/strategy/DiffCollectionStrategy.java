package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;


/**
 * Responsible for check the difference between two collections.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
final class DiffCollectionStrategy implements DiffStrategy {

    /**
     * Check the difference between two objects for the diffMetadata configuration.
     *
     * @param before object that is considered a state before the after object.
     * @param after object that is considered the before object updated.
     * @param diffMetadata the diffMetadata that is mapped to make the instance.
     *
     * @return the instance result between the two objects.
     */
    @Override
    public DiffResult diff(Object before, Object after, DiffMetadata diffMetadata) {
        Collection<?> beforeCollection = initializeCollection(before, diffMetadata.getMethod());
        Collection<?> afterCollection = initializeCollection(after, diffMetadata.getMethod());

        if (beforeCollection == null && afterCollection == null) {
            return new DiffResult(null, null, true);
        }

        // if has not the same size, so they are different
        if (!isEqualsSize(beforeCollection, afterCollection)) {
            return new DiffResult(beforeCollection, afterCollection, false);
        }

        Iterator<?> beforeIterator = beforeCollection.iterator();
        Iterator<?> afterIterator = afterCollection.iterator();

        while (beforeIterator.hasNext() && afterIterator.hasNext()) {
            Object beforeObject = beforeIterator.next();
            Object afterObject = afterIterator.next();

            // if has value property evaluation
            if (!diffMetadata.getValue().isEmpty()) {
                String value = diffMetadata.getValue();
                String nextValue = value.contains(".") ? value.substring(value.indexOf(".")) : null;
                DiffStrategyType diffStrategyType = DiffStrategyType.defineByValue(nextValue);
                Method method = DiffReflections.discoverGetter(beforeObject.getClass(), value);
                DiffMetadata metadata = new DiffMetadata(nextValue, method, diffStrategyType);
                DiffStrategy strategy = metadata.getStrategy();
                DiffResult single = strategy.diff(beforeObject, afterObject, metadata);

                if (!single.isEquals()) {
                    return new DiffResult(beforeCollection, afterCollection, false);
                }
            } else if (!beforeObject.equals(afterObject)) {
                return new DiffResult(beforeCollection, afterCollection, false);
            }
        }

        return new DiffResult(beforeCollection, afterCollection, true);
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
        Collection<?> collection = null;

        if (object != null) {
            collection = DiffReflections.invoke(object, method);
        }

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
        if (beforeCollection == null && afterCollection != null) {
            return false;
        }

        if (beforeCollection != null && afterCollection == null) {
            return false;
        }

        return beforeCollection.size() == afterCollection.size();
    }
}
