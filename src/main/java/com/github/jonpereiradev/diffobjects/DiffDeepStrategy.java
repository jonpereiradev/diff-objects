package com.github.jonpereiradev.diffobjects;

import java.lang.reflect.Method;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffDeepStrategy implements DiffStrategable {

    @Override
    public <T> DiffObject<T> diff(T before, T after, Method method) {
        Object beforeObject = before;
        Object afterObject = after;
        Method beforeMethod = method;
        Method afterMethod = method;

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
            return new DiffObject<T>(annotation, beforeObject, afterObject);
        }

        return DiffStrategyType.SINGLE.getDiffExecutable().diff(beforeObject, afterObject, method);
    }

    private static boolean isNotSameDiff(Method beforeMethod, Method afterMethod, Object beforeObject, Object afterObject) {
        return (beforeMethod != null
                && !beforeMethod.equals(afterMethod)
                && beforeObject != afterObject)
                || beforeObject != null
                && !beforeObject.equals(afterObject);
    }
}
