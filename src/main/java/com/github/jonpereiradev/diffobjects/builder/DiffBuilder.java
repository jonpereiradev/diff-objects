package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class DiffBuilder implements DiffInstanceBuilder, DiffMappingBuilder, DiffConfigurationBuilder {

    private final Class<?> classMap;
    private final List<DiffMetadata> metadatas;

    private DiffBuilder(Class<?> classMap) {
        this.classMap = classMap;
        this.metadatas = new LinkedList<>();
    }

    public static DiffInstanceBuilder map(Class<?> classMap) {
        return new DiffBuilder(classMap);
    }

    @Override
    public DiffMappingBuilder mapper() {
        return this;
    }

    @Override
    public DiffMappingBuilder mapping(String field) {
        return mapping(field, StringUtils.EMPTY);
    }

    @Override
    public DiffMappingBuilder mapping(String field, String value) {
        Method method = DiffReflections.discoverGetter(classMap, field);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (method == null) {
            throw new IllegalArgumentException("Method " + field + " not found in class " + classMap.getName());
        }

        if (value != null && value.split("\\.").length > 1) {
            diffStrategyType = DiffStrategyType.DEEP;
        }

        if (method.getReturnType().isAssignableFrom(Collection.class)) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        metadatas.add(new DiffMetadata(value, method, diffStrategyType));

        return this;
    }

    @Override
    public DiffInstanceBuilder builder() {
        return this;
    }

    @Override
    public DiffConfigurationBuilder getConfiguration() {
        return this;
    }

    @Override
    public List<DiffMetadata> getConfigurations() {
        return metadatas;
    }
}
