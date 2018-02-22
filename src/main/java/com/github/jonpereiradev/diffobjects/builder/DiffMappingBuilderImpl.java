package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author jonpereiradev@gmail.com
 *
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 */
final class DiffMappingBuilderImpl implements DiffMappingBuilder {

    private final Class<?> classMap;
    private final Map<String, DiffMetadata> metadata;
    private final DiffInstanceBuilder diffInstanceBuilder;

    DiffMappingBuilderImpl(Class<?> classMap, Map<String, DiffMetadata> metadatas, DiffInstanceBuilder diffInstanceBuilder) {
        this.classMap = classMap;
        this.metadata = metadatas;
        this.diffInstanceBuilder = diffInstanceBuilder;
    }

    /**
     * Maps all the field of a class.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffInstanceBuilder mappingAll() {
        Class<?> clazz = classMap;

        while (clazz != null && !clazz.equals(Object.class)) {
            for (Field parentField : clazz.getDeclaredFields()) {
                if (!metadata.containsKey(parentField.getName())) {
                    mapping(parentField.getName());
                }
            }

            clazz = clazz.getSuperclass();
        }

        return diffInstanceBuilder;
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     *
     * @return the instance of this mapping instance.
     */
    @Override
    public DiffQueryBuilder mapping(String field) {
        return mapping(field, StringUtils.EMPTY);
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     *
     * @return the instance of this mapping instance.
     *
     * @throws DiffException throw if the field doesn't have a public no args method for the field.
     */
    @Override
    public DiffQueryBuilder mapping(String field, String value) {
        Objects.requireNonNull(field, "Field name is required.");

        Method method = DiffReflections.discoverGetter(classMap, field);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (!Modifier.isPublic(method.getModifiers()) || method.getParameterTypes().length > 0) {
            throw new DiffException("Method " + method.getName() + " must be public and no-args.");
        }

        if (value != null && !value.isEmpty()) {
            diffStrategyType = DiffStrategyType.DEEP;
        }

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        DiffMetadata diffMetadata = new DiffMetadata(value, method, diffStrategyType);
        diffMetadata.getProperties().put("field", field);

        metadata.put(field, diffMetadata);

        return new DiffQueryBuilderImpl(diffMetadata, diffInstanceBuilder);
    }

    /**
     * Remove a mapping of the field for the class.
     *
     * @param field name of the field that will me used to remove.
     *
     * @return the instance of this mapping.
     */
    @Override
    public DiffMappingBuilder unmapping(String field) {
        metadata.remove(field);
        return this;
    }

    /**
     * Returns to the instance instance to allow the fluent interface.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffInstanceBuilder instance() {
        return diffInstanceBuilder;
    }
}
