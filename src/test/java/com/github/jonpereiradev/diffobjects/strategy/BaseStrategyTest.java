package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.builder.DiffConfigBuilder;

import java.util.List;

public abstract class BaseStrategyTest {

    protected DiffMetadata discoverByName(Class<?> classMap, String name) {
        DiffConfig config = DiffConfigBuilder.forClass(classMap).mapping().annotations().build();
        List<DiffMetadata> metadataList = config.build();

        for (DiffMetadata metadata : metadataList) {
            if (metadata.getMethod().getName().equals(name)) {
                return metadata;
            }
        }

        return null;
    }

}
