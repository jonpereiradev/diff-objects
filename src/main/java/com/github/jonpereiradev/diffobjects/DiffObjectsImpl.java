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
 * Responsible for checking the difference between two objects.
 *
 * @param <T> type of the object being compared
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffObjectsImpl<T> implements DiffObjects<T> {

    private final Class<T> ofType;

    DiffObjectsImpl(Class<T> ofType) {
        this.ofType = ofType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiffResults diff(T expected, T current) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return diff(expected, current, config);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public DiffResults diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return diff(expected, current, config, matcher);
    }

    /**
     * {@inheritDoc}
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

            // check the elements that exist on expectedCollection and not exists on currentCollection
            if (current == null) {
                result = DiffResult.forValue(expected, null, false);
                results.add(result);
                continue;
            }

            currentCollectionCopy.remove(current);

            boolean equals = true;

            // check the elements that exist on both collections
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

        // check the elements that exist on currentCollection but not exists on expectedCollection
        for (T current : currentCollectionCopy) {
            Stream<T> stream = expectedCollection.stream().filter((expected) -> matcher.isEquals(current, expected));
            T expected = stream.findFirst().orElse(null);

            if (expected == null) {
                result = DiffResult.forValue(null, current, false);
                results.add(result);
            }
        }

        return new DiffResultsImpl(results);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEquals(T expected, T current) {
        requireNonNull(expected, "Expected state is required");
        requireNonNull(current, "Current state is required");

        DiffConfig config = createDiffConfig();
        return isEquals(expected, current, config);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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

            // check the elements that exist on expectedCollection and not exists on currentCollection
            if (current == null) {
                return false;
            }

            currentCollectionCopy.remove(current);

            // check the elements that exist on both collections
            if (!isEquals(expected, current, config)) {
                return false;
            }
        }

        // check the elements that exist on currentCollection and not exists on expectedCollection
        for (T current : currentCollectionCopy) {
            Stream<T> stream = expectedCollection.stream().filter((expected) -> matcher.isEquals(current, expected));
            T expected = stream.findFirst().orElse(null);

            if (expected == null) {
                return false;
            }
        }

        return true;
    }

    private DiffConfig createDiffConfig() {
        return DiffConfigBuilder.forClass(ofType).mapping().annotations().build();
    }

}
