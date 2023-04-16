package com.github.jonpereiradev.diffobjects.builder;

final class DiffMappingTypeBuilderImpl<T> implements DiffMappingTypeBuilder<T> {

    private final DiffBuilderContext<T> context;

    DiffMappingTypeBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public DiffMappingBuilder<T> all() {
        return new DiffMappingAllBuilderImpl<>(context);
    }

    @Override
    public DiffMappingBuilder<T> annotations() {
        return new DiffMappingAnnotationsBuilderImpl<>(context);
    }

    @Override
    public DiffManualMappingBuilder<T> fields() {
        return new DiffMappingFieldBuilderImpl<>(context);
    }

}
