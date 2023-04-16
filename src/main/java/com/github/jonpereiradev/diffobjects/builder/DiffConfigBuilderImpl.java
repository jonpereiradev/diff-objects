package com.github.jonpereiradev.diffobjects.builder;


final class DiffConfigBuilderImpl<T> implements DiffConfigBuilder<T> {

    private final DiffBuilderContext<T> context;

    DiffConfigBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public DiffMappingTypeBuilder<T> mapping() {
        return new DiffMappingTypeBuilderImpl<>(context);
    }

}
