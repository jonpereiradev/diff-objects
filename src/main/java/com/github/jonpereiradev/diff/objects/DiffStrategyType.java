package com.github.jonpereiradev.diff.objects;

/**
 * @author jonpereiradev@gmail.com
 */
public enum DiffStrategyType {

    SINGLE(new DiffSingleStrategy()),
    DEEP_SEARCH(new DiffDeepStrategy()),
    COLLECTION_DEEP(new DiffCollectionDeepStrategy());

    private final DiffExecutable diffExecutable;

    private DiffStrategyType(DiffExecutable diffExecutable) {
        this.diffExecutable = diffExecutable;
    }

    public DiffExecutable getDiffExecutable() {
        return diffExecutable;
    }

}
