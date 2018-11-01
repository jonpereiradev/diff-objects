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
final class DiffMappingAllBuilderImpl implements DiffMappingAllBuilder {

    private final Map<String, DiffMetadata> metadatas;

    DiffMappingAllBuilderImpl(Map<String, DiffMetadata> metadatas) {
        this.metadatas = metadatas;
    }

    /**
     * Finds a mapping in the builder to make operations.
     *
     * @param field the name of the field mapped in the builder.
     * @return the instance of this mapping.
     */
    @Override
    public DiffQueryBuilder query(String field) {
        return new DiffQueryBuilderImpl(field, metadatas, this);
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
