package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

/**
 * Default implementation of {@link DiffQueryBuilder} for querying and modifying mapped fields
 * in a diff configuration.
 * <p>
 * Provides the ability to locate a mapped field by name and obtain a builder for further modifications.
 * Throws a {@link DiffException} if the requested field is not mapped.
 * </p>
 *
 * @param <T> the type of object being configured for diff operations
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
class DiffQueryBuilderImpl<T> implements DiffQueryBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffQueryBuilderImpl} with the given builder context.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffQueryBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    /**
     * Finds the mapping for a field by its name and returns a builder for further modifications.
     * Throws a {@link DiffException} if the field is not mapped.
     *
     * @param name the name of the field mapped in the builder
     *
     * @return a {@link DiffQueryFoundBuilder} instance for modifying the found field mapping
     *
     * @throws DiffException if the field is not mapped in the builder context
     */
    @Override
    public DiffQueryFoundBuilder<T> find(String name) {
        DiffMetadata metadata = context.get(name);

        if (metadata == null) {
            throw new DiffException("No object \"" + name + "\" mapped in builder. You need to map the field before query.");
        }

        return new DiffQueryFoundBuilderImpl<>(context, metadata);
    }
}