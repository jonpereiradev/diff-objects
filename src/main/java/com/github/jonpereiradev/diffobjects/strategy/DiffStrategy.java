package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;

/**
 * Defines a strategy to compare two objects and generate the result of this comparation.
 *
 * @author jonpereiradev@gmail.com
 *
 * @see DiffSingleStrategy
 * @see DiffDeepStrategy
 * @see DiffCollectionStrategy
 */
public interface DiffStrategy {

    <T> DiffResult<T> diff(Object before, Object after, Method method);

}
