package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.util.Objects;

/**
 * Responsible for check the difference between two simple objects and generate an object for this instance.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffSingleStrategy implements DiffStrategy {

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
    public DiffResult diff(Object before, Object after, DiffMetadata diffMetadata) {
        Object beforeValue = null;
        Object afterValue = null;

        if (before != null) {
            beforeValue = DiffReflections.invoke(before, diffMetadata.getMethod());
        }

        if (after != null) {
            afterValue = DiffReflections.invoke(after, diffMetadata.getMethod());
        }

        return new DiffResult(beforeValue, afterValue, Objects.deepEquals(beforeValue, afterValue));
    }
}
