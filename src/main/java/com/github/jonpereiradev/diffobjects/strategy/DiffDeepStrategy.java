package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Responsible for check the difference between two objects where @Diff navigates into object properties.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffDeepStrategy implements DiffStrategy {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

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
        Method beforeMethod = diffMetadata.getMethod();
        Method afterMethod = diffMetadata.getMethod();
        Object beforeObject = null;
        Object afterObject = null;

        if (before != null) {
            beforeObject = DiffReflections.invoke(beforeMethod, before);
        }
        if (after != null) {
            afterObject = DiffReflections.invoke(afterMethod, after);
        }

        if (beforeObject != null || afterObject != null) {
            for (String property : diffMetadata.getValue().split(REGEX_PROPERTY_SEPARATOR)) {
                if (beforeObject != null) {
                    beforeMethod = DiffReflections.discoverGetter(beforeObject, property);
                    beforeObject = DiffReflections.invoke(beforeMethod, beforeObject);
                }

                if (afterObject != null) {
                    afterMethod = DiffReflections.discoverGetter(afterObject, property);
                    afterObject = DiffReflections.invoke(afterMethod, afterObject);
                }
            }
        }

        return new DiffResult<>((T) beforeObject, (T) afterObject, Objects.deepEquals(beforeObject, afterObject));
    }
}
