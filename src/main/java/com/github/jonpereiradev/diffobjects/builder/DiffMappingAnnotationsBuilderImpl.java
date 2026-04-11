package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.annotation.DiffIgnore;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Implementation of {@link DiffMappingBuilder} that maps fields for diff operations
 * based on {@link DiffMapping} and {@link DiffMappings} annotations present on methods.
 * <p>
 * If the class is annotated with {@link DiffMappings}, all methods are mapped unless
 * annotated with {@link DiffIgnore}. Otherwise, only methods annotated with
 * {@link DiffMapping} or {@link DiffMappings} are mapped.
 * </p>
 *
 * <p>
 * This builder supports mapping of both simple fields and collections, and allows
 * custom comparators and properties to be specified via annotation attributes.
 * </p>
 *
 * @param <T> the type of object for which the diff configuration is being built
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffMappingAnnotationsBuilderImpl<T> implements DiffMappingBuilder<T> {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<T> context;

    /**
     * Constructs a new {@code DiffMappingAnnotationsBuilderImpl} and maps fields
     * based on annotation presence.
     *
     * @param context the builder context containing metadata mappings
     */
    DiffMappingAnnotationsBuilderImpl(DiffBuilderContext<T> context) {
        this.context = context;

        if (context.getOfType().isAnnotationPresent(DiffMappings.class)) {
            mapAllMethods();
        } else {
            mapAnnotationsMethods();
        }
    }

    /**
     * Maps all methods from the class for diff, except those annotated with {@link DiffIgnore}.
     */
    private void mapAllMethods() {
        DiffManualMappingBuilder<?> mapping = new DiffMappingFieldBuilderImpl<>(context);

        for (Method method : context.getOfType().getMethods()) {
            if (!method.isAnnotationPresent(DiffIgnore.class)) {
                mapping.map(method.getName());
            }
        }
    }

    /**
     * Maps all methods annotated with {@link DiffMapping} or {@link DiffMappings} from the class.
     */
    private void mapAnnotationsMethods() {
        DiffManualMappingBuilder<?> mapping = new DiffMappingFieldBuilderImpl<>(context);

        for (Method method : context.getOfType().getMethods()) {
            if (method.isAnnotationPresent(DiffMapping.class)) {
                mapping(mapping, method, method.getAnnotation(DiffMapping.class));
            } else if (method.isAnnotationPresent(DiffMappings.class)) {
                for (DiffMapping diffMapping : method.getAnnotation(DiffMappings.class).value()) {
                    mapping(mapping, method, diffMapping);
                }
            }
        }
    }

    /**
     * Maps a method and its associated {@link DiffMapping} annotation as either a field or collection.
     *
     * @param builder the manual mapping builder
     * @param method the method to map
     * @param diffMapping the diff mapping annotation
     */
    private static void mapping(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            mappingCollection(builder, method, diffMapping);
        } else {
            mappingField(builder, method, diffMapping);
        }
    }

    /**
     * Maps a method as a field for diff, applying custom comparator and properties if specified.
     *
     * @param builder the manual mapping builder
     * @param method the method to map
     * @param diffMapping the diff mapping annotation
     */
    private static void mappingField(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        String field = method.getName();
        DiffComparator<?> comparator = DiffReflections.newInstance(diffMapping.comparator());

        if (!diffMapping.value().isEmpty()) {
            field += "." + diffMapping.value();
        }

        DiffQueryFoundBuilder<?> query = builder.map(field, comparator).query().find(field);

        for (DiffProperty diffProperty : diffMapping.properties()) {
            query.property(diffProperty.key(), diffProperty.value());
        }
    }

    /**
     * Maps a method as a collection for diff, applying custom comparator and nested mapping if specified.
     *
     * @param builder the manual mapping builder
     * @param method the method to map
     * @param diffMapping the diff mapping annotation
     */
    private static void mappingCollection(DiffManualMappingBuilder<?> builder, Method method, DiffMapping diffMapping) {
        DiffComparator<?> comparator = DiffReflections.newInstance(diffMapping.comparator());
        DiffManualMappingBuilder<?> collectionBuilder = builder.map(method.getName(), comparator);

        if (!diffMapping.value().isEmpty()) {
            collectionBuilder.map(method.getName() + "." + diffMapping.value(), comparator);
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