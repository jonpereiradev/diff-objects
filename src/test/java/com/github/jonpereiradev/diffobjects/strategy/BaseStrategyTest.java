package com.github.jonpereiradev.diffobjects.strategy;

public abstract class BaseStrategyTest {

    protected DiffMetadata discoverByName(Class<?> classMap, String name) {
        for (DiffMetadata metadata : DiffReflections.mapAnnotations(classMap)) {
            if (metadata.getMethod().getName().equals(name)) {
                return metadata;
            }
        }

        return null;
    }

}
