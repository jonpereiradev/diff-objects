package com.github.jonpereiradev.diffobjects;

/**
 * @author jonpereiradev@gmail.com
 */
public enum DiffStrategyType {

    SINGLE(new DiffSingleStrategy()),
    DEEP_SEARCH(new DiffDeepStrategy()),
    COLLECTION_DEEP(new DiffCollectionStrategy());

    private final DiffStrategable diffExecutable;

    private DiffStrategyType(DiffStrategable diffExecutable) {
        this.diffExecutable = diffExecutable;
    }

    public DiffStrategable getDiffExecutable() {
        return diffExecutable;
    }

}
