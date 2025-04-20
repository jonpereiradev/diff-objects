package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;


class DiffQueryFoundBuilderImpl<T> implements DiffQueryFoundBuilder<T> {

    private final DiffBuilderContext<T> context;
    private final DiffMetadata metadata;

    DiffQueryFoundBuilderImpl(DiffBuilderContext<T> context, DiffMetadata metadata) {
        this.context = context;
        this.metadata = metadata;
    }

    @Override
    public DiffQueryFoundBuilder<T> property(String key, String value) {
        metadata.getProperties().put(key, value);
        return this;
    }

    @Override
    public DiffQueryBuilder<T> ignore() {
        String field = metadata.getProperties().get("field");
        context.remove(field);
        return new DiffQueryBuilderImpl<>(context);
    }

    @Override
    public DiffQueryFoundBuilder<T> find(String name) {
        return new DiffQueryBuilderImpl<>(context).find(name);
    }

    @Override
    public DiffConfig build() {
        return new DiffConfigImpl(context);
    }
}
