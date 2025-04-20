package com.github.jonpereiradev.diffobjects.strategy;


/**
 * The types of strategies provided by the API to execute the diff operation.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public enum DiffStrategyType {

    SINGLE(new DiffSingleStrategy()),
    NESTED(new DiffNestedStrategy()),
    COLLECTION(new DiffCollectionStrategy());

    private final DiffStrategy strategy;

    DiffStrategyType(DiffStrategy strategy) {
        this.strategy = strategy;
    }

    public DiffStrategy getStrategy() {
        return strategy;
    }

}
