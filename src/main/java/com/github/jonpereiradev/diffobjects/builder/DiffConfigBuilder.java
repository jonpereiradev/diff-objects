package com.github.jonpereiradev.diffobjects.builder;

import java.util.Objects;

/**
 * Defines the contract for building a diff configuration for a specific object type.
 * <p>
 * This builder interface allows mapping the properties of a class that should be included
 * in a diff operation. Use {@link #forClass(Class)} to create a builder instance for a given type,
 * then use {@link #mapping()} to start defining the mapping.
 * </p>
 *
 * @param <T> the type of object associated with the builder
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffConfigBuilder<T> {

    /**
     * Creates a diff config builder for the specified class type.
     *
     * @param <T> the type of class for which the builder will create mappings
     * @param ofType the class to be registered for diff configuration (must not be {@code null})
     *
     * @return a new {@code DiffConfigBuilder} instance for the given class
     *
     * @throws NullPointerException if {@code ofType} is {@code null}
     */
    static <T> DiffConfigBuilder<T> forClass(Class<T> ofType) {
        Objects.requireNonNull(ofType, "Class is required");
        DiffBuilderContext<T> context = new DiffBuilderContext<>(ofType);
        return new DiffConfigBuilderImpl<>(context);
    }

    /**
     * Starts the mapping definition for the diff configuration.
     *
     * @return a {@link DiffMappingTypeBuilder} to define property mappings for the diff
     */
    DiffMappingTypeBuilder<T> mapping();

}