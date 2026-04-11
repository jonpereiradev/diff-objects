package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.comparator.IndexComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

/**
 * Default implementation of {@link DiffManualMappingBuilder} for mapping fields of a class
 * for diff operations.
 * <p>
 * Supports mapping properties by name, with optional custom comparators, and determines
 * the appropriate diff strategy (single, nested, or collection) based on the property type and path.
 * </p>
 *
 * @param <T> the type of object for which the diff configuration is being built
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffMappingFieldBuilderImpl<T> implements DiffManualMappingBuilder<T> {

    /**
     * Regular expression used to split property paths (e.g., "address.city").
     */
    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffMappingFieldBuilderImpl} with the given builder context.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffMappingFieldBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;
    }

    /**
     * Maps a property by its name for inclusion in the diff operation.
     * <p>
     * If the property is a collection and not nested, uses {@link IndexComparator}.
     * Otherwise, uses {@link EqualsComparator}.
     * </p>
     *
     * @param name the name that identifies the property for mapping
     *
     * @return this builder instance for fluent chaining
     */
    @Override
    public DiffManualMappingBuilder<T> map(String name) {
        String[] fields = name.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(context.getOfType(), fields[0]);

        if (Collection.class.isAssignableFrom(method.getReturnType()) && fields.length == 1) {
            return map(name, new IndexComparator<>());
        }

        return map(name, new EqualsComparator<>());
    }

    /**
     * Maps a property by its name using a custom comparator for equality checks.
     * Determines the diff strategy (single, nested, or collection) based on the property path and type.
     *
     * @param name the name that identifies the property for mapping
     * @param comparator a custom comparator to determine equality for the property
     * @param <F> the type of the property being compared
     *
     * @return this builder instance for fluent chaining
     */
    @Override
    public <F> DiffManualMappingBuilder<T> map(String name, DiffComparator<F> comparator) {
        Objects.requireNonNull(name, "Field name is required.");

        String nestedFields = "";
        String[] fields = name.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(context.getOfType(), fields[0]);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (fields.length > 1) {
            diffStrategyType = DiffStrategyType.NESTED;
            nestedFields = name.substring(name.indexOf(".") + 1);
        }

        boolean isCollectionType = Collection.class.isAssignableFrom(method.getReturnType());

        if (isCollectionType) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        DiffMetadata diffMetadata = new DiffMetadata(
            nestedFields,
            method,
            diffStrategyType,
            comparator
        );

        diffMetadata.getProperties().put("field", name);

        if (isCollectionType && !nestedFields.isEmpty()) {
            context.remove(fields[0]);
        }

        context.put(name, diffMetadata);

        return this;
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