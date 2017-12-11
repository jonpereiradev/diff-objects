package com.github.jonpereiradev.diffobjects.strategy;

/**
 * @author jonpereiradev@gmail.com
 */
public enum DiffStrategyType {

    SINGLE(new DiffSingleStrategy()),
    DEEP(new DiffDeepStrategy()),
    COLLECTION(new DiffCollectionStrategy());

    private final DiffStrategy strategy;

    DiffStrategyType(DiffStrategy strategy) {
        this.strategy = strategy;
    }

    public DiffStrategy getStrategy() {
        return strategy;
    }

}
