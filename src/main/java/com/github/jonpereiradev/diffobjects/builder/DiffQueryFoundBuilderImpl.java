package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

/**
 * Default implementation of {@link DiffQueryFoundBuilder} for modifying a specific queried field mapping
 * in a diff configuration.
 * <p>
 * Allows setting custom properties for the field, removing the field from the mapping,
 * finding and modifying other mapped fields, and building the resulting {@link DiffConfig} instance.
 * </p>
 *
 * @param <T> the type of object being configured for diff operations
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
class DiffQueryFoundBuilderImpl<T> implements DiffQueryFoundBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * The metadata for the currently queried field.
     */
    private final DiffMetadata metadata;

    /**
     * Constructs a new {@code DiffQueryFoundBuilderImpl} with the given context and metadata.
     *
     * @param context the builder context containing metadata mappings
     * @param metadata the metadata for the currently queried field
     */
    DiffQueryFoundBuilderImpl(DiffBuilderContext<T> context, DiffMetadata metadata) {
        this.context = context;
        this.metadata = metadata;
    }

    /**
     * Sets a custom property for the last mapped field in the diff configuration.
     *
     * @param key the identifier of the property
     * @param value the value of the property
     *
     * @return this builder instance for fluent chaining
     */
    @Override
    public DiffQueryFoundBuilder<T> property(String key, String value) {
        metadata.getProperties().put(key, value);
        return this;
    }

    /**
     * Removes the mapping for the field represented by this query from the diff configuration.
     *
     * @return a {@link DiffQueryBuilder} instance for further configuration
     */
    @Override
    public DiffQueryBuilder<T> ignore() {
        String field = metadata.getProperties().get("field");
        context.remove(field);
        return new DiffQueryBuilderImpl<>(context);
    }

    /**
     * Finds the mapping for a field by its name and returns a builder for further modifications.
     *
     * @param name the name of the field mapped in the builder
     *
     * @return a {@link DiffQueryFoundBuilder} instance for modifying the found field mapping
     */
    @Override
    public DiffQueryFoundBuilder<T> find(String name) {
        return new DiffQueryBuilderImpl<>(context).find(name);
    }

    /**
     * Builds and returns the {@link DiffConfig} instance representing the current configuration.
     *
     * @return a {@link DiffConfig} instance with the applied modifications
     */
    @Override
    public DiffConfig build() {
        return new DiffConfigImpl(context);
    }
}