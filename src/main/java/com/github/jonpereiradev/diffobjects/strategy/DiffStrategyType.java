package com.github.jonpereiradev.diffobjects.strategy;

/**
 * Enum representing the types of diff strategies provided by the API to execute diff operations.
 * <p>
 * Each enum constant is associated with a specific {@link DiffStrategy} implementation:
 * <ul>
 *     <li>{@link #SINGLE} - Strategy for comparing simple (non-nested) properties.</li>
 *     <li>{@link #NESTED} - Strategy for comparing nested properties by traversing object graphs.</li>
 *     <li>{@link #COLLECTION} - Strategy for comparing collections of objects.</li>
 * </ul>
 * Use {@link #getStrategy()} to retrieve the associated strategy implementation.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public enum DiffStrategyType {

    /**
     * Strategy for comparing simple (non-nested) properties.
     */
    SINGLE(new DiffSingleStrategy()),

    /**
     * Strategy for comparing nested properties by traversing object graphs.
     */
    NESTED(new DiffNestedStrategy()),

    /**
     * Strategy for comparing collections of objects.
     */
    COLLECTION(new DiffCollectionStrategy());

    /**
     * The strategy implementation associated with this type.
     */
    private final DiffStrategy strategy;

    /**
     * Constructs a {@code DiffStrategyType} with the given strategy implementation.
     *
     * @param strategy the strategy implementation
     */
    DiffStrategyType(DiffStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Returns the {@link DiffStrategy} implementation associated with this type.
     *
     * @return the diff strategy implementation
     */
    public DiffStrategy getStrategy() {
        return strategy;
    }

}