package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Context class for building diff configurations for a specific object type.
 * <p>
 * Stores the target class and a mapping of property names to their associated {@link DiffMetadata}.
 * Provides methods to access, add, remove, and query metadata mappings during the diff configuration build process.
 * </p>
 *
 * @param <T> the type of object for which the diff configuration is being built
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public class DiffBuilderContext<T> {

    /**
     * The class type for which the diff configuration is being built.
     */
    private final Class<T> ofType;

    /**
     * The mapping of property names to their associated diff metadata.
     */
    private final Map<String, DiffMetadata> metadata;

    /**
     * Constructs a new {@code DiffBuilderContext} for the specified class type.
     *
     * @param ofType the class type for which the context is created
     */
    public DiffBuilderContext(Class<T> ofType) {
        this.ofType = ofType;
        this.metadata = new LinkedHashMap<>();
    }

    /**
     * Returns an unmodifiable view of the metadata mapping.
     *
     * @return an unmodifiable map of property names to {@link DiffMetadata}
     */
    public Map<String, DiffMetadata> getMetadataMap() {
        return Collections.unmodifiableMap(metadata);
    }

    /**
     * Retrieves the {@link DiffMetadata} associated with the given property name.
     *
     * @param name the property name
     *
     * @return the associated {@link DiffMetadata}, or {@code null} if not present
     */
    public DiffMetadata get(String name) {
        return metadata.get(name);
    }

    /**
     * Checks if the context contains a mapping for the given property name.
     *
     * @param name the property name
     *
     * @return {@code true} if the mapping exists; {@code false} otherwise
     */
    public boolean containsKey(String name) {
        return metadata.containsKey(name);
    }

    /**
     * Removes the mapping for the given property name.
     *
     * @param name the property name
     *
     * @return the removed {@link DiffMetadata}, or {@code null} if not present
     */
    public DiffMetadata remove(String name) {
        return metadata.remove(name);
    }

    /**
     * Adds or replaces the mapping for the given property name with the specified metadata.
     *
     * @param name the property name
     * @param diffMetadata the metadata to associate with the property
     *
     * @return the previous {@link DiffMetadata} associated with the name, or {@code null} if none
     */
    public DiffMetadata put(String name, DiffMetadata diffMetadata) {
        return metadata.put(name, diffMetadata);
    }

    /**
     * Returns the class type for which the diff configuration is being built.
     *
     * @return the class type
     */
    public Class<T> getOfType() {
        return ofType;
    }
}