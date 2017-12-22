package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

/**
 * Responsible for check the difference between two collections.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffCollectionStrategy implements DiffStrategy {

    /**
     * Check the difference between two objects for the diffMetadata configuration.
     *
     * @param before       object that is considered a state before the after object.
     * @param after        object that is considered the before object updated.
     * @param diffMetadata the diffMetadata that is mapped to make the instance.
     * @param <T>          the type of object returned by the instance.
     * @return the instance result between the two objects.
     */
    @Override
    public <T> DiffResult<T> diff(Object before, Object after, DiffMetadata diffMetadata) {
        Collection<?> beforeCollection = initializeCollection(before, diffMetadata.getMethod());
        Collection<?> afterCollection = initializeCollection(after, diffMetadata.getMethod());

        if (beforeCollection == null && afterCollection == null) {
            return new DiffResult<>(null, null, true);
        }

        if (!isEqualsSize(beforeCollection, afterCollection)) {
            return new DiffResult<>((T) beforeCollection, (T) afterCollection, false);
        }

        Iterator<?> beforeIterator = beforeCollection.iterator();
        Iterator<?> afterIterator = afterCollection.iterator();

        while (beforeIterator.hasNext() && afterIterator.hasNext()) {
            Object beforeObject = beforeIterator.next();
            Object afterObject = afterIterator.next();

            if (!diffMetadata.getValue().isEmpty()) {
                Method method = DiffReflections.discoverGetter(beforeObject.getClass(), diffMetadata.getValue());
                DiffMetadata metadata = new DiffMetadata(null, method, DiffStrategyType.SINGLE);
                DiffResult<Object> single = DiffStrategyType.SINGLE.getStrategy().diff(beforeObject, afterObject, metadata);

                if (!single.isEquals()) {
                    return new DiffResult<>((T) beforeCollection, (T) afterCollection, false);
                }
            } else if (!beforeObject.equals(afterObject)) {
                return new DiffResult<>((T) beforeCollection, (T) afterCollection, false);
            }
        }

        return new DiffResult<>((T) beforeCollection, (T) afterCollection, true);
    }

    private boolean isEqualsSize(Collection<?> beforeCollection, Collection<?> afterCollection) {
        if (beforeCollection == null && afterCollection != null) {
            return false;
        }

        if (beforeCollection != null && afterCollection == null) {
            return false;
        }

        return beforeCollection.size() == afterCollection.size();
    }

    /**
     * Inicializa a coleção de objetos para verificação do instance.
     *
     * @param object
     * @param method
     * @param <T>
     * @return
     */
    private <T> Collection<T> initializeCollection(Object object, Method method) {
        Collection<T> collection = null;

        if (object != null) {
            collection = DiffReflections.invoke(object, method);
        }

        if (collection != null && collection.isEmpty()) {
            return null;
        }

        return collection;
    }
}
