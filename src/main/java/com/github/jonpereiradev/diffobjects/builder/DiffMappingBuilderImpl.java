package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
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
final class DiffMappingBuilderImpl implements DiffMappingBuilder {

    private final Class<?> classMap;
    private final Map<String, DiffMetadata> metadatas;

    DiffMappingBuilderImpl(Class<?> classMap, Map<String, DiffMetadata> metadatas) {
        this.classMap = classMap;
        this.metadatas = metadatas;
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @return the instance of this mapping instance.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field) {
        return mapping(field, StringUtils.EMPTY);
    }

    /**
     * Maps the getter of the field for the class with the nestedField property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     * @return the instance of this mapping instance.
     * @throws DiffException throw if the field doesn't have a public no args method for the field.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field, String nestedField) {
        Objects.requireNonNull(field, "Field name is required.");

        Method method = DiffReflections.discoverGetter(classMap, field);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (!Modifier.isPublic(method.getModifiers()) || method.getParameterTypes().length > 0) {
            throw new DiffException("Method " + method.getName() + " must be public and no-args.");
        }

        if (nestedField != null && !nestedField.isEmpty()) {
            diffStrategyType = DiffStrategyType.DEEP;
        }

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        DiffMetadata diffMetadata = new DiffMetadata(nestedField, method, diffStrategyType);
        diffMetadata.getProperties().put("field", field);

        metadatas.put(field, diffMetadata);

        return new DiffQueryMappingBuilderImpl(diffMetadata, this, metadatas);
    }

    /**
     * Gets the configuration instance to get the configuration generated by this instance instance.
     *
     * @return a configuration instance instance.
     */
    @Override
    public DiffConfiguration configuration() {
        return new DiffConfigurationImpl(metadatas);
    }
}
