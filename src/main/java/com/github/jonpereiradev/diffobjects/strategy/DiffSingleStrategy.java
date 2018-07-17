package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;

import java.util.Objects;


/**
 * Responsible for check the difference between two simple objects (with no nested property).
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
final class DiffSingleStrategy implements DiffStrategy {

    /**
     * Check the difference between two objects for the diffMetadata configuration.
     *
     * @param before object that is considered a state before the after object.
     * @param after object that is considered the before object updated.
     * @param diffMetadata the metadata with the configuration for the diff.
     *
     * @return the diff result between the two objects.
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
