package com.github.jonpereiradev.diffobjects.builder;

/**
 * Default implementation of {@link DiffConfigBuilder} for building diff configurations for a specific object type.
 * <p>
 * Holds the builder context and provides access to mapping configuration via {@link #mapping()}.
 * </p>
 *
 * @param <T> the type of object for which the diff configuration is being built
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffConfigBuilderImpl<T> implements DiffConfigBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffConfigBuilderImpl} with the given builder context.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffConfigBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    /**
     * Starts the mapping definition for the diff configuration.
     *
     * @return a {@link DiffMappingTypeBuilder} to define property mappings for the diff
     */
    @Override
    public DiffMappingTypeBuilder<T> mapping() {
        return new DiffMappingTypeBuilderImpl<>(context);
    }

}