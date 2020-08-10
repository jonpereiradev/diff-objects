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
     * @param expected the expected object state to compare with current object.
     * @param current the current object state to compare with expected object.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(T expected, T current) {
        Objects.requireNonNull(expected, "Expected state is required.");
        Objects.requireNonNull(current, "Current state is required.");

        return diff(expected, current, annotations);
    }

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param expected the expected object state to compare with after object.
     * @param current the current object state to compare with before object.
     * @param configuration the configuration of the diff.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(T expected, T current, DiffConfiguration configuration) {
        Objects.requireNonNull(expected, "Expected state is required.");
        Objects.requireNonNull(current, "Current state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<DiffMetadata> metadatas = configuration.build();
        List<DiffResult> results = new ArrayList<>(metadatas.size());

        for (DiffMetadata metadata : metadatas) {
            DiffStrategy strategy = metadata.getStrategy();
            DiffResult diff = strategy.diff(expected, current, metadata);

            diff.setProperties(Collections.unmodifiableMap(metadata.getProperties()));

            results.add(diff);
        }

        return results;
    }

    /**
     * Execute the diff between two objects using annotations.
     *
     * @param expected the expected object state to compare with after object.
     * @param current the current object state to compare with before object.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher) {
        Objects.requireNonNull(expected, "Expected state is required.");
        Objects.requireNonNull(current, "Current state is required.");
        return diff(expected, current, annotations, matcher);
    }

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param expectedCollection the expected object state to compare with after object.
     * @param currentCollection the current object state to compare with before object.
     * @param configuration the configuration of the diff.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return a list with the results of the diff.
     */
    public List<DiffResult> diff(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffConfiguration configuration,
        DiffComparator<T> matcher) {
        Objects.requireNonNull(expectedCollection, "Before state is required.");
        Objects.requireNonNull(currentCollection, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<DiffResult> results = new ArrayList<>();

        for (T expected : expectedCollection) {
            Stream<T> stream = currentCollection.stream().filter((current) -> matcher.equals(expected, current));
            T after = stream.findFirst().orElse(null);

            // check the elements that exist on expectedCollection and not exists on currentCollection
            if (after == null) {
                results.add(new DiffResult(expected, null, false));
                continue;
            }

            boolean equals = true;

            // check the elements that exist on both collections
            for (DiffMetadata metadata : configuration.build()) {
                DiffStrategy strategy = metadata.getStrategy();
                DiffResult diff = strategy.diff(expected, after, metadata);

                if (!diff.isEquals()) {
                    equals = false;
                    break;
                }
            }

            results.add(new DiffResult(expected, after, equals));
        }

        // check the elements that exist on currentCollection and not exists on expectedCollection
        for (T current : currentCollection) {
            Stream<T> stream = expectedCollection.stream().filter((expected) -> matcher.equals(current, expected));
            T expected = stream.findFirst().orElse(null);

            if (expected == null) {
                results.add(new DiffResult(null, current, false));
            }
        }

        return results;
    }

    /**
     * Check if exists any difference between the two objects using annotations.
     *
     * @param expected the expected object state to compare with after object.
     * @param current the current object state to compare with before object.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(T expected, T current) {
        Objects.requireNonNull(expected, "Expected state is required.");
        Objects.requireNonNull(current, "Current state is required.");
        return isEquals(expected, current, annotations);
    }

    /**
     * Check if exists any difference between the two objects.
     *
     * @param expected the expected object state to compare with after object.
     * @param current the current object state to compare with before object.
     * @param configuration the configuration of the diff.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(T expected, T current, DiffConfiguration configuration) {
        Objects.requireNonNull(expected, "Before state is required.");
        Objects.requireNonNull(current, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        for (DiffMetadata metadata : configuration.build()) {
            DiffStrategy strategy = metadata.getStrategy();
            DiffResult result = strategy.diff(expected, current, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if exists any difference between the two objects using annotations.
     *
     * @param expectedCollection the expected object state to compare with after object.
     * @param currentCollection the current object state to compare with before object.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffComparator<T> matcher) {
        Objects.requireNonNull(expectedCollection, "Expected Collection state is required.");
        Objects.requireNonNull(currentCollection, "Current Collection state is required.");
        return isEquals(expectedCollection, currentCollection, annotations, matcher);
    }

    /**
     * Check if exists any difference between the two objects.
     *
     * @param expectedCollection the expected object state to compare with after object.
     * @param currentCollection the current object state to compare with before object.
     * @param configuration the configuration of the diff.
     * @param matcher the matcher that will define how an object from list is equals to other.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    public boolean isEquals(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffConfiguration configuration,
        DiffComparator<T> matcher) {
        Objects.requireNonNull(expectedCollection, "Before state is required.");
        Objects.requireNonNull(currentCollection, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<T> currentCollectionCopy = new ArrayList<>(currentCollection);

        for (T expected : expectedCollection) {
            Stream<T> stream = currentCollection.stream().filter((current) -> matcher.equals(expected, current));
            T current = stream.findFirst().orElse(null);

            // check the elements that exist on expectedCollection and not exists on currentCollection
            if (current == null) {
                return false;
            }

            currentCollectionCopy.remove(current);

            // check the elements that exist on both collections
            if (!isEquals(expected, current, configuration)) {
                return false;
            }
        }

        // check the elements that exist on currentCollection and not exists on expectedCollection
        for (T current : currentCollectionCopy) {
            Stream<T> stream = expectedCollection.stream().filter((expected) -> matcher.equals(current, expected));
            T expected = stream.findFirst().orElse(null);

            if (expected == null) {
                return false;
            }
        }

        return true;
    }

}
