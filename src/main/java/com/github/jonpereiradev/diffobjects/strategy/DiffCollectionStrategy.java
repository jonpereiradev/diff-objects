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
     * @param diffMetadata the diffMetadata that is mapped to make the builder.
     * @param <T>          the type of object returned by the builder.
     * @return the builder result between the two objects.
     */
    @Override
    public <T> DiffResult<T> diff(Object before, Object after, DiffMetadata diffMetadata) {
        Collection<?> beforeCollection = initializeCollection(before, diffMetadata.getMethod());
        Collection<?> afterCollection = initializeCollection(after, diffMetadata.getMethod());

        if (beforeCollection == null && afterCollection == null) {
            return new DiffResult<>(null, null, true);
        }

        if (isEqualsSize(beforeCollection, afterCollection)) {
            Iterator<?> beforeIterator = beforeCollection.iterator();
            Iterator<?> afterIterator = afterCollection.iterator();

            while (beforeIterator.hasNext() && afterIterator.hasNext()) {
                if (diffMetadata.getValue().isEmpty()) {
                    Object beforeObject = beforeIterator.next();
                    Object afterObject = afterIterator.next();

                    if (!beforeObject.equals(afterObject)) {
                        return new DiffResult<>((T) beforeCollection, (T) afterCollection, false);
                    }
                }
            }
        }

        return new DiffResult<>((T) beforeCollection, (T) afterCollection, true);
    }

    private boolean isEqualsSize(Collection<?> beforeCollection, Collection<?> afterCollection) {
        if (beforeCollection == null && !afterCollection.isEmpty()) {
            return false;
        }

        return beforeCollection != null && afterCollection != null && beforeCollection.isEmpty();
    }

    /**
     * Inicializa a coleção de objetos para verificação do builder.
     *
     * @param object
     * @param method
     * @param <T>
     * @return
     */
    private <T> Collection<T> initializeCollection(Object object, Method method) {
        Collection<T> collection = null;

        if (object != null) {
            collection = DiffReflections.invoke(method, object);
        }

        if (collection != null && collection.isEmpty()) {
            return null;
        }

        return collection;
    }
}
