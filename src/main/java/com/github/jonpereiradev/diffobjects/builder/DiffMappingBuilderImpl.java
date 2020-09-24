package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.comparator.IndexComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
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
 * @since 1.0.0
 */
final class DiffMappingBuilderImpl<T> implements DiffMappingBuilder<T> {

    private static final String REGEX_PROPERTY_SEPARATOR = "\\.";

    private final Class<T> classMap;
    private final Map<String, DiffMetadata> metadatas;

    DiffMappingBuilderImpl(Class<T> classMap, Map<String, DiffMetadata> metadatas) {
        this.classMap = classMap;
        this.metadatas = metadatas;
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
        String[] fields = field.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(classMap, fields[0]);

        if (Collection.class.isAssignableFrom(method.getReturnType()) && fields.length == 1) {
            return mapping(field, new IndexComparator<>());
        }

        return mapping(field, new EqualsComparator<>());
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
        Objects.requireNonNull(field, "Field name is required.");

        String nestedFields = StringUtils.EMPTY;
        String[] fields = field.split(REGEX_PROPERTY_SEPARATOR);
        Method method = DiffReflections.discoverGetter(classMap, fields[0]);
        DiffStrategyType diffStrategyType = DiffStrategyType.SINGLE;

        if (fields.length > 1) {
            diffStrategyType = DiffStrategyType.NESTED;
            nestedFields = field.substring(field.indexOf(".") + 1);
        }

        boolean isCollectionType = Collection.class.isAssignableFrom(method.getReturnType());

        if (isCollectionType) {
            diffStrategyType = DiffStrategyType.COLLECTION;
        }

        DiffMetadata diffMetadata = new DiffMetadata(
            nestedFields,
            method,
            diffStrategyType,
            fieldComparator
        );

        diffMetadata.getProperties().put("field", field);

        if (isCollectionType && !nestedFields.isEmpty()) {
            metadatas.remove(fields[0]);
        }

        metadatas.put(field, diffMetadata);

        return new DiffQueryMappingBuilderImpl<>(diffMetadata, this);
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

    Map<String, DiffMetadata> getMetadatas() {
        return metadatas;
    }

}
