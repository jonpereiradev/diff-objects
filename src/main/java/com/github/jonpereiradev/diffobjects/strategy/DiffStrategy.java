package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

/**
 * Defines a strategy to compare two objects and generate the result of this diff.
 *
 * @author jonpereiradev@gmail.com
 * @see DiffSingleStrategy
 * @see DiffDeepStrategy
 * @see DiffCollectionStrategy
 */
public interface DiffStrategy {

    DiffResult diff(Object before, Object after, DiffMetadata diffMetadata);

}
