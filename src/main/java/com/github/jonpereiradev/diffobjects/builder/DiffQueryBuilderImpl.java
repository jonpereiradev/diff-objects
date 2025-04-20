package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;


class DiffQueryBuilderImpl<T> implements DiffQueryBuilder<T> {

    private final DiffBuilderContext<T> context;

    DiffQueryBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public DiffQueryFoundBuilder<T> find(String name) {
        DiffMetadata metadata = context.get(name);

        if (metadata == null) {
            throw new DiffException("No object \"" + name + "\" mapped in builder. You need to map the field before query.");
        }

        return new DiffQueryFoundBuilderImpl<>(context, metadata);
    }
}
