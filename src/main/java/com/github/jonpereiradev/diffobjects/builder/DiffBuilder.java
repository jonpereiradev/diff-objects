package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import org.apache.commons.lang.StringUtils;

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
 * @since 1.0
 */
public final class DiffBuilder implements DiffInstanceBuilder {

    private final Class<?> classMap;
    private final Map<String, DiffMetadata> metadatas;

    private DiffBuilder(Class<?> classMap) {
        this.classMap = classMap;
        this.metadatas = new LinkedHashMap<>();
    }

    /**
     * Creates a diff instance instance to map the diff elements of a class.
     *
     * @param clazz the class that will be registry to make diffs.
     *
     * @return the diff instance instance.
     */
    public static DiffBuilder map(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class is required.");
        return new DiffBuilder(clazz);
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
    public DiffQueryMappingBuilder mapping(String field) {
        return mapping(field, new EqualsComparator());
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field, DiffComparator comparator) {
        return mapping(field, StringUtils.EMPTY, comparator);
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     *
     * @return the instance of this mapping instance.
     *
     * @throws DiffException throw if the field doesn't have a public no args method for the field.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field, String nestedField) {
        return mapping(field, nestedField, new EqualsComparator());
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping instance.
     *
     * @throws DiffException throw if the field doesn't have a public no args method for the field.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field, String nestedField, DiffComparator comparator) {
        return new DiffMappingBuilderImpl(classMap, metadatas).mapping(field, nestedField, comparator);
    }

    Map<String, DiffMetadata> getMetadatas() {
        return metadatas;
    }
}
