package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.Map;


/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author Jonathan Pereira
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 * @since 1.0
 */
final class DiffQueryMappingBuilderImpl implements DiffQueryMappingBuilder {

    private final DiffMetadata diffMetadata;
    private final DiffMappingBuilder diffMappingBuilder;
    private final Map<String, DiffMetadata> metadatas;

    DiffQueryMappingBuilderImpl(DiffMetadata diffMetadata, DiffMappingBuilder diffMappingBuilder, Map<String, DiffMetadata> metadatas) {
        this.diffMetadata = diffMetadata;
        this.diffMappingBuilder = diffMappingBuilder;
        this.metadatas = metadatas;
    }

    /**
     * Define a property for the last mapping.
     *
     * @param key the identifier of the property.
     * @param value the value of the property.
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryMappingBuilder property(String key, String value) {
        diffMetadata.getProperties().put(key, value);
        return this;
    }

    /**
     * Removes the property from the mapping.
     *
     * @return the instance of this builder.
     */
    @Override
    public DiffMappingBuilder unmapping() {
        metadatas.remove(diffMetadata.getProperties().get("field"));
        return diffMappingBuilder;
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field) {
        return diffMappingBuilder.mapping(field);
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryMappingBuilder mapping(String field, String value) {
        return diffMappingBuilder.mapping(field, value);
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
