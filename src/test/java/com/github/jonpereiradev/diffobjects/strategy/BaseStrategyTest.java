package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.builder.DiffReflections;

public abstract class BaseStrategyTest {

    protected DiffMetadata discoverByName(Class<?> classMap, String name) {
        for (DiffMetadata metadata : DiffReflections.mapAnnotations(classMap).build()) {
            if (metadata.getMethod().getName().equals(name)) {
                return metadata;
            }
        }

        return null;
    }

}
