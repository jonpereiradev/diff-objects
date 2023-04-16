package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;


/**
 * Responsible to map a class and fields to be able to generate diffs.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
class DiffQueryFoundBuilderImpl<T> implements DiffQueryFoundBuilder<T> {

    private final DiffBuilderContext<T> context;
    private final DiffMetadata metadata;

    DiffQueryFoundBuilderImpl(DiffBuilderContext<T> context, DiffMetadata metadata) {
        this.context = context;
        this.metadata = metadata;
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
    public DiffQueryFoundBuilder<T> property(String key, String value) {
        metadata.getProperties().put(key, value);
        return this;
    }

    /**
     * Removes the property from the mapping.
     *
     * @return the instance of this builder.
     */
    @Override
    public DiffQueryBuilder<T> ignore() {
        String field = metadata.getProperties().get("field");
        context.remove(field);
        return new DiffQueryBuilderImpl<>(context);
    }

    @Override
    public DiffQueryFoundBuilder<T> find(String name) {
        return new DiffQueryBuilderImpl<>(context).find(name);
    }

    /**
     * Gets the configuration instance to get the configuration generated by this instance.
     *
     * @return a configuration instance instance.
     */
    @Override
    public DiffConfig build() {
        return new DiffConfigImpl(context);
    }
}
