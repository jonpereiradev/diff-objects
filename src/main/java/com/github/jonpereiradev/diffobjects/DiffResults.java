package com.github.jonpereiradev.diffobjects;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface DiffResults extends Iterable<DiffResult> {

    List<DiffResult> getResults();

    default Stream<DiffResult> stream() {
        return getResults().stream();
    }

    default boolean isEmpty() {
        return getResults().isEmpty();
    }

    default int size() {
        return getResults().size();
    }

    @Override
    default Iterator<DiffResult> iterator() {
        return getResults().iterator();
    }

    @Override
    default void forEach(Consumer<? super DiffResult> action) {
        getResults().forEach(action);
    }

    @Override
    default Spliterator<DiffResult> spliterator() {
        return getResults().spliterator();
    }

}
