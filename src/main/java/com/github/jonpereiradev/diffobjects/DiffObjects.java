package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import com.github.jonpereiradev.diffobjects.builder.DiffReflections;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


/**
 * Responsible for execute the diff between two objects.
 *
 * @param <T> type of object been compared.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
public final class DiffObjects<T> {

    private final DiffConfiguration annotations;

    private DiffObjects(Class<T> clazz) {
        this.annotations = DiffReflections.mapAnnotations(clazz);
    }

    /**
     * Creates a DiffObjects for a specific class.
     *
     * @param clazz class type of this DiffObjects.
     * @param <T> type of the class.
     *
     * @return the DiffObjects for the class.
     */
    public static <T> DiffObjects<T> forClass(Class<T> clazz) {
        return new DiffObjects<>(clazz);
    }

    /**
     * Execute the diff between two objects using annotations.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return diff(beforeState, afterState, annotations);
    }

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param configuration the configuration of the diff.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(T beforeState, T afterState, DiffConfiguration configuration) {
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
     * Execute the diff between two objects using annotations.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(Collection<T> beforeState, Collection<T> afterState, DiffComparator<T> matcher) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return diff(beforeState, afterState, annotations, matcher);
    }

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param configuration the configuration of the diff.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(Collection<T> beforeState, Collection<T> afterState, DiffConfiguration configuration, DiffComparator<T> matcher) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<DiffResult> results = new ArrayList<>();

        for (T before : beforeState) {
            Stream<T> stream = afterState.stream().filter((after) -> matcher.equals(before, after));
            T after = stream.findFirst().orElse(null);

            // check the elements that exist on beforeState and not exists on afterState
            if (after == null) {
                results.add(new DiffResult(before, null, false));
                continue;
            }

            boolean equals = true;

            // check the elements that exist on both collections
            for (DiffMetadata metadata : configuration.build()) {
                DiffStrategy strategy = metadata.getStrategy();
                DiffResult diff = strategy.diff(before, after, metadata);

                if (!diff.isEquals()) {
                    equals = false;
                    break;
                }
            }

            results.add(new DiffResult(before, after, equals));
        }

        // check the elements that exist on afterState and not exists on beforeState
        for (T after : afterState) {
            Stream<T> stream = beforeState.stream().filter((before) -> matcher.equals(after, before));
            T before = stream.findFirst().orElse(null);

            if (before == null) {
                results.add(new DiffResult(null, after, false));
            }
        }

        return results;
    }

    /**
     * Check if exists any difference between the two objects using annotations.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return isEquals(beforeState, afterState, annotations);
    }

    /**
     * Check if exists any difference between the two objects.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param configuration the configuration of the diff.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(T beforeState, T afterState, DiffConfiguration configuration) {
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

    /**
     * Check if exists any difference between the two objects using annotations.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(Collection<T> beforeState, Collection<T> afterState, DiffComparator<T> matcher) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return isEquals(beforeState, afterState, annotations, matcher);
    }

    /**
     * Check if exists any difference between the two objects.
     *
     * @param beforeState the before object state to compare with after object.
     * @param afterState the after object state to compare with before object.
     * @param configuration the configuration of the diff.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(Collection<T> beforeState, Collection<T> afterState, DiffConfiguration configuration, DiffComparator<T> matcher) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<T> afterCopy = new ArrayList<>(afterState);

        for (T before : beforeState) {
            Stream<T> stream = afterState.stream().filter((after) -> matcher.equals(before, after));
            T after = stream.findFirst().orElse(null);

            // check the elements that exist on beforeState and not exists on afterState
            if (after == null) {
                return false;
            }

            afterCopy.remove(after);

            // check the elements that exist on both collections
            if (!isEquals(before, after, configuration)) {
                return false;
            }
        }

        // check the elements that exist on afterState and not exists on beforeState
        for (T after : afterCopy) {
            Stream<T> stream = beforeState.stream().filter((before) -> matcher.equals(after, before));
            T before = stream.findFirst().orElse(null);

            if (before == null) {
                return false;
            }
        }

        return true;
    }

}
