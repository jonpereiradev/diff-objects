package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.annotation.Diff;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Responsible for check the difference between two objects where @Diff navigates into object properties.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffDeepStrategy implements DiffStrategy {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

    @Override
    public <T> DiffResult<T> diff(Object before, Object after, Method method) {
        Method beforeMethod = method;
        Method afterMethod = method;
        Object beforeObject = null;
        Object afterObject = null;

        if (before != null) {
            beforeObject = DiffReflections.invoke(beforeMethod, before);
        }
        if (after != null) {
            afterObject = DiffReflections.invoke(afterMethod, after);
        }

        if (beforeObject != null || afterObject != null) {
            for (String property : method.getAnnotation(Diff.class).value().split(REGEX_PROPERTY_SEPARATOR)) {
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
