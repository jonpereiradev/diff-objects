package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author Jonathan Pereira
 * @since 1.0
 *
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 */
final class DiffQueryBuilderImpl implements DiffQueryBuilder {

    private final DiffMetadata diffMetadata;
    private final DiffInstanceBuilder diffInstanceBuilder;

    DiffQueryBuilderImpl(DiffMetadata diffMetadata, DiffInstanceBuilder diffInstanceBuilder) {
        this.diffMetadata = diffMetadata;
        this.diffInstanceBuilder = diffInstanceBuilder;
    }

    /**
     * Maps all the field of a class.
     *
     * @return the instance instance responsible for this mapping.
     */
    @Override
    public DiffInstanceBuilder mappingAll() {
        return diffInstanceBuilder;
    }

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     *
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryBuilder mapping(String field) {
        return diffInstanceBuilder.mapper().mapping(field);
    }

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     *
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryBuilder mapping(String field, String value) {
        return diffInstanceBuilder.mapper().mapping(field, value);
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
        return diffInstanceBuilder.mapper().unmapping(field);
    }

    /**
     * Define a property for the last mapping.
     *
     * @param key the identifier of the property.
     * @param value the value of the property.
     *
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryBuilder property(String key, String value) {
        diffMetadata.getProperties().put(key, value);
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

    /**
     * Removes the property from the mapping.
     *
     * @return the instance of this builder.
     */
    @Override
    public DiffInstanceBuilder unmapping() {
        return diffInstanceBuilder.mapper().unmapping(diffMetadata.getProperties().get("field")).instance();
    }
}
