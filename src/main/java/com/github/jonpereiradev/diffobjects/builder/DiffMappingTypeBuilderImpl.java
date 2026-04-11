package com.github.jonpereiradev.diffobjects.builder;

/**
 * Default implementation of {@link DiffMappingTypeBuilder} for configuring the types of field mappings
 * supported for diff operations.
 * <p>
 * Provides methods to select between mapping all fields via reflection, mapping only annotated fields,
 * or manually specifying field mappings. Delegates to the appropriate builder implementation based on the chosen strategy.
 * </p>
 *
 * @param <T> the type of object for which the mapping is being configured
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffMappingTypeBuilderImpl<T> implements DiffMappingTypeBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffMappingTypeBuilderImpl} with the given builder context.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffMappingTypeBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    /**
     * Identifies and maps all fields of a class using reflection.
     *
     * @return a {@link DiffMappingBuilder} instance with all fields mapped
     */
    @Override
    public DiffMappingBuilder<T> all() {
        return new DiffMappingAllBuilderImpl<>(context);
    }

    /**
     * Identifies and maps all fields of a class that have supported annotations.
     *
     * @return a {@link DiffMappingBuilder} instance with only annotated fields mapped
     */
    @Override
    public DiffMappingBuilder<T> annotations() {
        return new DiffMappingAnnotationsBuilderImpl<>(context);
    }

    /**
     * Enables manual mapping of fields for diff operations.
     *
     * @return a {@link DiffManualMappingBuilder} instance for manual field mapping configuration
     */
    @Override
    public DiffManualMappingBuilder<T> fields() {
        return new DiffMappingFieldBuilderImpl<>(context);
    }

}