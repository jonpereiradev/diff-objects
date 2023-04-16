package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiffBuilderContext<T> {

    private final Class<T> ofType;
    private final Map<String, DiffMetadata> metadata;

    public DiffBuilderContext(Class<T> ofType) {
        this.ofType = ofType;
        this.metadata = new LinkedHashMap<>();
    }

    public Map<String, DiffMetadata> getMetadataMap() {
        return Collections.unmodifiableMap(metadata);
    }

    public DiffMetadata get(String name) {
        return metadata.get(name);
    }

    public boolean containsKey(String name) {
        return metadata.containsKey(name);
    }

    public DiffMetadata remove(String name) {
        return metadata.remove(name);
    }

    public DiffMetadata put(String name, DiffMetadata diffMetadata) {
        return metadata.put(name, diffMetadata);
    }

    public Class<T> getOfType() {
        return ofType;
    }
}
