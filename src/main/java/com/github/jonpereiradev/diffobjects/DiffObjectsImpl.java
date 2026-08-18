package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.builder.DiffConfigBuilder;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link DiffObjects} responsible for comparing objects or collections of objects
 * and determining their differences.
 * <p>
 * Provides methods to perform deep comparisons using annotation-based or programmatic configuration,
 * and to retrieve detailed diff results or equality status.
 * </p>
 *
 * @param <T> the type of object being compared
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffObjectsImpl<T> implements DiffObjects<T> {

    /**
     * The class type of the objects being compared.
     */
    private final Class<T> ofType;

    /**
     * Constructs a new {@code DiffObjectsImpl} for the specified class type.
     *
     * @param ofType the class type to compare
     */
    DiffObjectsImpl(Class<T> ofType) {
        this.ofType = ofType;
    }

    /**
     * Compares two objects and returns the differences using annotation-based configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     *
     * @return a {@link DiffResults} containing all detected differences
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public DiffResults diff(T expected, T current) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return diff(expected, current, config);
    }

    /**
     * Compares two objects using the provided diff configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     * @param config the diff configuration to use
     *
     * @return a {@link DiffResults} containing all detected differences
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public DiffResults diff(T expected, T current, DiffConfig config) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");
        requireNonNull(config, "Config is required");

        List<DiffMetadata> metadata = config.build();
        List<DiffResult> results = new ArrayList<>(metadata.size());

        for (DiffMetadata value : metadata) {
            DiffStrategy strategy = value.getStrategy();
            DiffResult diff = strategy.diff(expected, current, value);
            results.add(diff);
        }

        return new DiffResultsImpl(results);
    }

    /**
     * Compares two collections of objects and returns the differences using annotation-based configuration.
     *
     * @param expected the expected collection of objects
     * @param current the current collection of objects
     * @param matcher a comparator to match objects between collections
     *
     * @return a {@link DiffResults} containing all detected differences
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public DiffResults diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return diff(expected, current, config, matcher);
    }

    /**
     * Compares two collections of objects using the provided diff configuration.
     *
     * @param expectedCollection the expected collection of objects
     * @param currentCollection the current collection of objects
     * @param config the diff configuration to use
     * @param matcher a comparator to match objects between collections
     *
     * @return a {@link DiffResults} containing all detected differences
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public DiffResults diff(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffConfig config,
        DiffComparator<T> matcher) {

        requireNonNull(expectedCollection, "Expected state is required");
        requireNonNull(currentCollection, "Current state is required");
        requireNonNull(config, "Config is required");

        List<DiffResult> results = new ArrayList<>();
        List<T> currentCollectionCopy = new ArrayList<>(currentCollection);
        List<DiffMetadata> metadataList = config.build();
        DiffResult result;

        for (T expected : expectedCollection) {
            Stream<T> stream = currentCollection.stream().filter((current) -> matcher.isEquals(expected, current));
            T current = stream.findFirst().orElse(null);

            // Check elements present in expectedCollection but not in currentCollection
            if (current == null) {
                result = DiffResult.forValue(expected, null, false);
                results.add(result);
                continue;
            }

            currentCollectionCopy.remove(current);

            boolean equals = true;

            // Check elements present in both collections
            for (DiffMetadata metadata : metadataList) {
                DiffStrategy strategy = metadata.getStrategy();
                DiffResult diff = strategy.diff(expected, current, metadata);

                if (!diff.isEquals()) {
                    equals = false;
                    break;
                }
            }

            result = DiffResult.forValue(expected, current, equals);
            results.add(result);
        }

        // Check elements present in currentCollection but not in expectedCollection
        for (T current : currentCollectionCopy) {
            result = DiffResult.forValue(null, current, false);
            results.add(result);
        }

        return new DiffResultsImpl(results);
    }

    /**
     * Determines if two objects are equal according to the diff strategy using annotation-based configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public boolean isEquals(T expected, T current) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return isEquals(expected, current, config);
    }

    /**
     * Determines if two objects are equal using the provided diff configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     * @param config the diff configuration to use
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public boolean isEquals(T expected, T current, DiffConfig config) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");
        requireNonNull(config, "Config is required");

        for (DiffMetadata metadata : config.build()) {
            DiffStrategy strategy = metadata.getStrategy();
            DiffResult result = strategy.diff(expected, current, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if two collections of objects are equal according to the diff strategy using annotation-based configuration.
     *
     * @param expectedCollection the expected collection of objects
     * @param currentCollection the current collection of objects
     * @param matcher a comparator to match objects between collections
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public boolean isEquals(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffComparator<T> matcher) {

        requireNonNull(expectedCollection, "Expected Collection state is required.");
        requireNonNull(currentCollection, "Current Collection state is required.");

        DiffConfig config = createDiffConfig();
        return isEquals(expectedCollection, currentCollection, config, matcher);
    }

    /**
     * Determines if two collections of objects are equal using the provided diff configuration.
     *
     * @param expectedCollection the expected collection of objects
     * @param currentCollection the current collection of objects
     * @param config the diff configuration to use
     * @param matcher a comparator to match objects between collections
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Override
    public boolean isEquals(
        Collection<T> expectedCollection,
        Collection<T> currentCollection,
        DiffConfig config,
        DiffComparator<T> matcher) {

        requireNonNull(expectedCollection, "Expected state is required");
        requireNonNull(currentCollection, "Current state is required");
        requireNonNull(config, "Config is required");

        List<T> currentCollectionCopy = new ArrayList<>(currentCollection);

        for (T expected : expectedCollection) {
            Stream<T> stream = currentCollection.stream().filter((current) -> matcher.isEquals(expected, current));
            T current = stream.findFirst().orElse(null);

            // Check elements present in expectedCollection but not in currentCollection
            if (current == null) {
                return false;
            }

            currentCollectionCopy.remove(current);

            // Check elements present in both collections
            if (!isEquals(expected, current, config)) {
                return false;
            }
        }

        return currentCollectionCopy.isEmpty();
    }

    /**
     * Creates a diff configuration using annotation-based mapping for the class type.
     *
     * @return a {@link DiffConfig} instance for the class type
     */
    private DiffConfig createDiffConfig() {
        return DiffConfigBuilder.forClass(ofType).mapping().annotations().build();
    }

}