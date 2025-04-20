package com.github.jonpereiradev.diffobjects.builder;


import java.util.Objects;

/**
 * Diff config builder contract for creating a configuration for a diff operation.
 *
 * @param <T> the type of object associated with the builder.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffConfigBuilder<T> {

    /**
     * Creates a diff builder to map the diff elements of a class.
     *
     * @param <T> the type of class for which the builder will create mappings.
     * @param ofType the class that will be registered to create diffs.
     *
     * @return the diff config builder instance.
     */
    static <T> DiffConfigBuilder<T> forClass(Class<T> ofType) {
        Objects.requireNonNull(ofType, "Class is required");
        DiffBuilderContext<T> context = new DiffBuilderContext<>(ofType);
        return new DiffConfigBuilderImpl<>(context);
    }

    DiffMappingTypeBuilder<T> mapping();

}
