package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategy;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Responsible for execute the diff between two objects.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
public final class DiffObjects {

    private DiffObjects() {
        throw new UnsupportedOperationException("No need to create an instance of this class.");
    }

    /**
     * Execute the diff between two objects using annotations.
     *
     * @param <T> type of object been compared.
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with befor object.
     *
     * @return a list with the results of the diff.
     */
    public static <T> List<DiffResult> diff(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return diff(beforeState, afterState, DiffReflections.mapAnnotations(beforeState.getClass()));
    }

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param <T> type of object been compared.
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with befor object.
     * @param configuration the configuration of the diff.
     *
     * @return a list with the results of the diff.
     */
    public static <T> List<DiffResult> diff(T beforeState, T afterState, DiffConfiguration configuration) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<DiffMetadata> metadatas = configuration.build();
        List<DiffResult> results = new ArrayList<>(metadatas.size());

        for (DiffMetadata metadata : metadatas) {
            DiffStrategy strategy = metadata.getStrategy();
            DiffResult diff = strategy.diff(beforeState, afterState, metadata);

            diff.setProperties(Collections.unmodifiableMap(metadata.getProperties()));

            results.add(diff);
        }

        return results;
    }

    /**
     * Check if exists any difference between the two objects using annotations.
     *
     * @param <T> type of object been compared.
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with befor object.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public static <T> boolean isEquals(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return isEquals(beforeState, afterState, DiffReflections.mapAnnotations(beforeState.getClass()));
    }

    /**
     * Check if exists any difference between the two objects.
     *
     * @param <T> type of object been compared.
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with befor object.
     * @param configuration the configuration of the diff.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public static <T> boolean isEquals(T beforeState, T afterState, DiffConfiguration configuration) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        for (DiffMetadata metadata : configuration.build()) {
            DiffStrategy strategy = metadata.getStrategy();
            DiffResult result = strategy.diff(beforeState, afterState, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

}
