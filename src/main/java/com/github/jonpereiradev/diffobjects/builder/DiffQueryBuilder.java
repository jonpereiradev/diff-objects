package com.github.jonpereiradev.diffobjects.builder;

/**
 * Builder responsible for modify the queried field of a class to create a configuration of diff.
 *
 * @author jonpereiradev@gmail.com
 *
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffConfiguration
 */
public interface DiffQueryBuilder extends DiffMappingBuilder {

    /**
     * Define a property for the last mapping.
     *
     * @param key the identifier of the property.
     * @param value the value of the property.
     *
     * @return the instance of this builder.
     */
    DiffQueryBuilder property(String key, String value);

    /**
     * Remove o mapeamento da propriedade gerenciada pelo query.
     *
     * @return the instance of this builder.
     */
    DiffInstanceBuilder unmapping();
}
