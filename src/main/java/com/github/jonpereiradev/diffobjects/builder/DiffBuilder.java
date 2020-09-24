package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author Jonathan Pereira
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 * @since 1.0.0
 */
public final class DiffBuilder<T> implements DiffInstanceBuilder<T> {

    private final Class<T> classMap;
    private final Map<String, DiffMetadata> metadatas;

    private DiffBuilder(Class<T> classMap) {
        this.classMap = classMap;
        this.metadatas = new LinkedHashMap<>();
    }

    /**
     * Creates a diff instance instance to map the diff elements of a class.
     *
     * @param <T> the type of class the builder will create the mappings.
     * @param clazz the class that will be registry to make diffs.
     *
     * @return the diff instance instance.
     */
    public static <T> DiffBuilder<T> map(Class<T> clazz) {
        Objects.requireNonNull(clazz, "Class is required.");
        return new DiffBuilder<>(clazz);
    }

    /**
     * Maps all the field of a class.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffMappingAllBuilder mappingAll() {
        Class<?> clazz = classMap;

        while (clazz != null && !clazz.equals(Object.class)) {
            for (Field parentField : clazz.getDeclaredFields()) {
                if (!metadatas.containsKey(parentField.getName())) {
                    mapping(parentField.getName());
                }
            }

            clazz = clazz.getSuperclass();
        }

        return new DiffMappingAllBuilderImpl(metadatas);
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     *
     * @return the instance of this mapping instance.
     */
    @Override
    public DiffQueryMappingBuilder<T> mapping(String field) {
        return new DiffMappingBuilderImpl<>(classMap, metadatas).mapping(field);
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param fieldComparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    @Override
    public <F> DiffQueryMappingBuilder<T> mapping(
        String field,
        DiffComparator<F> fieldComparator) {
        return new DiffMappingBuilderImpl<>(classMap, metadatas).mapping(field, fieldComparator);
    }

    Map<String, DiffMetadata> getMetadatas() {
        return metadatas;
    }
}
