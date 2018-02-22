package com.github.jonpereiradev.diffobjects.strategy;

import org.apache.commons.lang.StringUtils;

/**
 * The strategies types provided for the API to execute the diff operation.
 *
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

    static DiffStrategyType defineByValue(String value) {
        if (StringUtils.contains(value, ".")) {
            return DEEP;
        }

        return SINGLE;
    }

    public DiffStrategy getStrategy() {
        return strategy;
    }

}
