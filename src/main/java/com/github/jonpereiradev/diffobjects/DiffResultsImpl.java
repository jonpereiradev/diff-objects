package com.github.jonpereiradev.diffobjects;

import java.util.Collections;
import java.util.List;

final class DiffResultsImpl implements DiffResults {

    private final List<DiffResult> results;

    DiffResultsImpl(List<DiffResult> results) {
        this.results = Collections.unmodifiableList(results);
    }

    @Override
    public List<DiffResult> getResults() {
        return results;
    }

}
