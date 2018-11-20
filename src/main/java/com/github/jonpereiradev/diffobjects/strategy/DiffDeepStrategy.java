package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;


/**
 * Responsible for check the difference between two objects where value navigates into object properties.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
final class DiffDeepStrategy implements DiffStrategy {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

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
        DiffComparator comparator = diffMetadata.getComparator();
        Method beforeMethod = diffMetadata.getMethod();
        Method afterMethod = diffMetadata.getMethod();
        Object beforeObject = null;
        Object afterObject = null;

        if (before != null) {
            beforeObject = DiffReflections.invoke(before, beforeMethod);
        }
        if (after != null) {
            afterObject = DiffReflections.invoke(after, afterMethod);
        }

        if (beforeObject != null || afterObject != null) {
            for (String property : diffMetadata.getValue().split(REGEX_PROPERTY_SEPARATOR)) {
                if (beforeObject != null) {
                    beforeMethod = DiffReflections.discoverGetter(beforeObject.getClass(), property);
                    beforeObject = DiffReflections.invoke(beforeObject, beforeMethod);
                }

                if (afterObject != null) {
                    afterMethod = DiffReflections.discoverGetter(afterObject.getClass(), property);
                    afterObject = DiffReflections.invoke(afterObject, afterMethod);
                }
            }
        }

        return new DiffResult(beforeObject, afterObject, comparator.equals(beforeObject, afterObject));
    }
}
