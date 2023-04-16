package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;

import java.lang.reflect.Field;


final class DiffMappingAllBuilderImpl<T> implements DiffMappingBuilder<T> {

    private final DiffBuilderContext<T> context;

    DiffMappingAllBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;

        Class<?> ofType = context.getOfType();
        DiffManualMappingBuilder<T> fieldMapping = new DiffMappingFieldBuilderImpl<>(context);

        while (ofType != null && !ofType.equals(Object.class)) {
            for (Field parentField : ofType.getDeclaredFields()) {
                if (!context.containsKey(parentField.getName())) {
                    fieldMapping.map(parentField.getName());
                }
            }

            ofType = ofType.getSuperclass();
        }
    }

    @Override
    public DiffQueryBuilder<T> query() {
        return new DiffQueryBuilderImpl<>(context);
    }

    @Override
    public DiffConfig build() {
        return new DiffConfigImpl(context);
    }
}
