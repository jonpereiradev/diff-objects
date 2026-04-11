package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.builder.DiffBuilderContext;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link DiffConfig} responsible for generating and providing
 * the diff configuration metadata for a specific instance.
 * <p>
 * This class collects metadata about the fields to be compared, applies ordering
 * if the {@link DiffOrder} annotation is present, and returns the configuration
 * as an unmodifiable list.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffConfigImpl implements DiffConfig {

    /**
     * The builder context containing metadata mappings for the target class.
     */
    private final DiffBuilderContext<?> context;

    /**
     * The list of metadata describing the properties to be compared.
     */
    private final List<DiffMetadata> metadataList;

    /**
     * Constructs a new {@code DiffConfigImpl} with the given builder context.
     *
     * @param context the builder context containing metadata mappings
     */
    public DiffConfigImpl(DiffBuilderContext<?> context) {
        this.context = context;
        this.metadataList = new ArrayList<>(context.getMetadataMap().size());
    }

    /**
     * Builds and returns the list of metadata describing the properties to be compared.
     * <p>
     * If any field is annotated with {@link DiffOrder}, the list is sorted accordingly.
     * The metadata is generated only once and cached for subsequent calls.
     * </p>
     *
     * @return the list of {@link DiffMetadata} representing the diff configuration
     */
    @Override
    public List<DiffMetadata> build() {
        if (metadataList.isEmpty()) {
            boolean sortable = false;

            for (Map.Entry<String, DiffMetadata> entry : context.getMetadataMap().entrySet()) {
                DiffOrder annotation = entry.getValue().getMethod().getAnnotation(DiffOrder.class);

                if (annotation != null) {
                    entry.getValue().setOrder(annotation.value());
                    sortable = true;
                }

                metadataList.add(entry.getValue());
            }

            if (sortable) {
                Collections.sort(metadataList);
            }
        }

        return metadataList;
    }

}