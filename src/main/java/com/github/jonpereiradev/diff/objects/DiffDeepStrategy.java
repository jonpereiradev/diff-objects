package com.github.jonpereiradev.diff.objects;

import java.lang.reflect.Method;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffDeepStrategy implements DiffExecutable {

    private static boolean isNotSameDiff(Method beforeMethod, Method afterMethod, Object beforeObject, Object afterObject) {
        return (beforeMethod != null && !beforeMethod.equals(afterMethod) && beforeObject != afterObject)
                || beforeObject != null && !beforeObject.equals(afterObject);
    }

    @Override
    public DiffObject diff(DiffProperty annotation, Object before, Object after) {
        Method beforeMethod = null;
        Method afterMethod = null;
        Object beforeObject = before;
        Object afterObject = after;

        if (before == null && after == null) {
            return null;
        }

        for (String property : annotation.value().split("\\.")) {
            if (beforeObject != null) {
                beforeMethod = DiffReflections.discoverGetter(beforeObject, property);
                beforeObject = DiffReflections.invoke(beforeMethod, beforeObject);
            }

            if (afterObject != null) {
                afterMethod = DiffReflections.discoverGetter(afterObject, property);
                afterObject = DiffReflections.invoke(afterMethod, afterObject);
            }
        }

        if (isNotSameDiff(beforeMethod, afterMethod, beforeObject, afterObject)) {
            return new DiffObject(annotation, beforeObject, afterObject);
        }

        return DiffStrategyType.SINGLE.getDiffExecutable().diff(annotation, beforeObject, afterObject);
    }

}
