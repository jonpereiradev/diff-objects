package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.util.Objects;

/**
 * Responsible for check the difference between two simple objects and generate an object for this builder.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffSingleStrategy implements DiffStrategy {

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
        T beforeValue = null;
        T afterValue = null;

        if (before != null) {
            beforeValue = DiffReflections.invoke(diffMetadata.getMethod(), before);
        }

        if (after != null) {
            afterValue = DiffReflections.invoke(diffMetadata.getMethod(), after);
        }

        return new DiffResult<>(beforeValue, afterValue, Objects.deepEquals(beforeValue, afterValue));
    }
}
