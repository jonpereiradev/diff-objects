package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author jonpereiradev@gmail.com
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 */
public final class DiffBuilder implements DiffInstanceBuilder, DiffMappingBuilder, DiffConfiguration {

    private final Class<?> classMap;
    private final List<DiffMetadata> metadatas;

    private DiffBuilder(Class<?> classMap) {
        this.classMap = classMap;
        this.metadatas = new LinkedList<>();
    }

    /**
     * Creates a diff instance instance to map the diff elements of a class.
     *
     * @param clazz the class that will be registry to make diffs.
     * @return the diff instance instance.
     */
    public static DiffInstanceBuilder map(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class is required.");
        return new DiffBuilder(clazz);
    }

    /**
     * Gets the mapping instance to registry the fields used in the diff.
     *
     * @return a mapping instance instance.
     */
    @Override
    public DiffMappingBuilder mapper() {
        return this;
    }

    /**
     * Maps all the field of a class.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffInstanceBuilder mappingAll() {
        Class<?> clazz = classMap;

        if (!metadatas.isEmpty()) {
            throw new IllegalStateException("The mappingAll cannot be used after a mapping(field) call.");
        }

        while (clazz != null && !clazz.equals(Object.class)) {
            for (Field parentField : clazz.getDeclaredFields()) {
                mapping(parentField.getName());
            }

            clazz = clazz.getSuperclass();
        }

        return this;
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @return the instance of this mapping instance.
     */
    @Override
    public DiffMappingBuilder mapping(String field) {
        return mapping(field, StringUtils.EMPTY);
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     * @return the instance of this mapping instance.
     * @throws DiffException throw if the field doesn't have a public no args method for the field.
     */
    @Override
    public DiffMappingBuilder mapping(String field, String value) {
        Method method = DiffReflections.discoverGetter(classMap, field);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (method == null) {
            throw new DiffException("Method " + field + " not found in class " + classMap.getName());
        }

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

        if (metadatas.contains(diffMetadata)) {
            throw new DiffException("Field \"" + field + "\" already mapped in this builder.");
        }

        diffMetadata.getProperties().put("field", field);

        metadatas.add(diffMetadata);

        return this;
    }

    /**
     * Define a property for the last mapping.
     *
     * @param key   the identifier of the property.
     * @param value the value of the property.
     * @return the instance of this mapping.
     */
    @Override
    public DiffMappingBuilder property(String key, String value) {
        if (metadatas.isEmpty()) {
            throw new DiffException("A mapping field is required to associate the property.");
        }

        metadatas.get(metadatas.size() - 1).getProperties().put(key, value);

        return this;
    }

    /**
     * Returns to the instance instance to allow the fluent interface.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffInstanceBuilder instance() {
        return this;
    }

    /**
     * Gets the configuration instance to get the configuration generated by this instance instance.
     *
     * @return a configuration instance instance.
     */
    @Override
    public DiffConfiguration configuration() {
        return this;
    }

    /**
     * Gets the configuration for the instance instance.
     *
     * @return the metadata generated by the instance instance.
     */
    @Override
    public List<DiffMetadata> build() {
        return Collections.unmodifiableList(metadatas);
    }
}
