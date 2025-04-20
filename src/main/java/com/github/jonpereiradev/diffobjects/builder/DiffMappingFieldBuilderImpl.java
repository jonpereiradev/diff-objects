package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.comparator.IndexComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;


final class DiffMappingFieldBuilderImpl<T> implements DiffManualMappingBuilder<T> {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

    private final DiffBuilderContext<T> context;

    DiffMappingFieldBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public DiffManualMappingBuilder<T> map(String name) {
        String[] fields = name.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(context.getOfType(), fields[0]);

        if (Collection.class.isAssignableFrom(method.getReturnType()) && fields.length == 1) {
            return map(name, new IndexComparator<>());
        }

        return map(name, new EqualsComparator<>());
    }

    @Override
    public <F> DiffManualMappingBuilder<T> map(String name, DiffComparator<F> comparator) {
        Objects.requireNonNull(name, "Field name is required.");

        String nestedFields = "";
        String[] fields = name.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(context.getOfType(), fields[0]);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (fields.length > 1) {
            diffStrategyType = DiffStrategyType.NESTED;
            nestedFields = name.substring(name.indexOf(".") + 1);
        }

        boolean isCollectionType = Collection.class.isAssignableFrom(method.getReturnType());

        if (isCollectionType) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        DiffMetadata diffMetadata = new DiffMetadata(
            nestedFields,
            method,
            diffStrategyType,
            comparator
        );

        diffMetadata.getProperties().put("field", name);

        if (isCollectionType && !nestedFields.isEmpty()) {
            context.remove(fields[0]);
        }

        context.put(name, diffMetadata);

        return this;
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
