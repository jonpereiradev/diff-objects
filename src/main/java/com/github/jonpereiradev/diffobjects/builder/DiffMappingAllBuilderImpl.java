package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;

import java.lang.reflect.Field;

/**
 * Implementation of {@link DiffMappingBuilder} that automatically maps all fields of a class (and its superclasses)
 * for diff operations.
 * <p>
 * This builder inspects the target class and its superclasses (excluding {@code Object.class}),
 * and maps every declared field that is not already present in the builder context.
 * </p>
 *
 * @param <T> the type of object for which the diff configuration is being built
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffMappingAllBuilderImpl<T> implements DiffMappingBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffMappingAllBuilderImpl} and maps all fields of the target class and its superclasses.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffMappingAllBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;

        Class<?> ofType = context.getOfType();
        DiffManualMappingBuilder<T> fieldMapping = new DiffMappingFieldBuilderImpl<>(context);

        // Traverse the class hierarchy and map all declared fields not already present in the context
        while (ofType != null && !ofType.equals(Object.class)) {
            for (Field parentField : ofType.getDeclaredFields()) {
                if (!context.containsKey(parentField.getName())) {
                    fieldMapping.map(parentField.getName());
                }
            }
            ofType = ofType.getSuperclass();
        }
    }

    /**
     * Provides access to query and modify the currently mapped fields.
     *
     * @return a {@link DiffQueryBuilder} instance for searching and mutating mapped fields
     */
    @Override
    public DiffQueryBuilder<T> query() {
        return new DiffQueryBuilderImpl<>(context);
    }

    /**
     * Builds and returns the final {@link DiffConfig} instance with the mapped fields.
     *
     * @return a {@link DiffConfig} representing the configuration for the mapped fields
     */
    @Override
    public DiffConfig build() {
        return new DiffConfigImpl(context);
    }
}