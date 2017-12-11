package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Responsible for check the difference between two simple objects and generate an object for this diff.
 *
 * @author jonpereiradev@gmail.com
 */
final class DiffSingleStrategy implements DiffStrategy {

    @Override
    public <T> DiffResult<T> diff(Object before, Object after, Method method) {
        T beforeValue = DiffReflections.invoke(method, before);
        T afterValue = DiffReflections.invoke(method, after);

        return new DiffResult<>(beforeValue, afterValue, Objects.deepEquals(beforeValue, afterValue));
    }
}
