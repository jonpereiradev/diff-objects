package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.annotation.DiffIgnore;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;
import java.util.Collection;


final class DiffMappingAnnotationsBuilderImpl<T> implements DiffMappingBuilder<T> {

    private final DiffBuilderContext<T> context;

    DiffMappingAnnotationsBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;

        if (context.getOfType().isAnnotationPresent(DiffMappings.class)) {
            mapAllMethods();
        } else {
            mapAnnotationsMethods();
        }
    }

    /**
     * Map all methods from a class for diff.
     */
    private void mapAllMethods() {
        DiffManualMappingBuilder<?> mapping = new DiffMappingFieldBuilderImpl<>(context);

        for (Method method : context.getOfType().getMethods()) {
            if (!method.isAnnotationPresent(DiffIgnore.class)) {
                mapping.map(method.getName());
            }
        }
    }

    /**
     * Map all method annotations from a class.
     */
    private void mapAnnotationsMethods() {
        DiffManualMappingBuilder<?> mapping = new DiffMappingFieldBuilderImpl<>(context);

        for (Method method : context.getOfType().getMethods()) {
            if (method.isAnnotationPresent(DiffMapping.class)) {
                mapping(mapping, method, method.getAnnotation(DiffMapping.class));
            } else if (method.isAnnotationPresent(DiffMappings.class)) {
                for (DiffMapping diffMapping : method.getAnnotation(DiffMappings.class).value()) {
                    mapping(mapping, method, diffMapping);
                }
            }
        }
    }

    private static void mapping(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            mappingCollection(builder, method, diffMapping);
        } else {
            mappingField(builder, method, diffMapping);
        }
    }

    private static void mappingField(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        String field = method.getName();
        DiffComparator<?> comparator = DiffReflections.newInstance(diffMapping.comparator());

        if (!diffMapping.value().isEmpty()) {
            field += "." + diffMapping.value();
        }

        DiffQueryFoundBuilder<?> query = builder.map(field, comparator).query().find(field);

        for (DiffProperty diffProperty : diffMapping.properties()) {
            query.property(diffProperty.key(), diffProperty.value());
        }
    }

    private static void mappingCollection(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        DiffComparator<?> comparator = DiffReflections.newInstance(diffMapping.comparator());
        DiffManualMappingBuilder<?> collectionBuilder = builder.map(method.getName(), comparator);

        if (!diffMapping.value().isEmpty()) {
            collectionBuilder.map(method.getName() + "." + diffMapping.value(), comparator);
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
