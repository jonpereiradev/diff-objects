package com.github.jonpereiradev.diff.objects;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResult {

    private final List<DiffObject> results = new LinkedList<DiffObject>();

    void diff(DiffMetadata metadata, Object before, Object after) {
        DiffExecutable diffExecutable = DiffStrategyType.SINGLE.getDiffExecutable();

        if (metadata.getStrategy() != null) {
            diffExecutable = metadata.getStrategy().type().getDiffExecutable();
        }

        DiffObject diff = diffExecutable.diff(metadata.getAnnotation(), before, after);

        if (diff != null) {
            results.add(diff);
        }
    }

    public boolean isEquals() {
        return results.isEmpty();
    }

    public List<DiffObject> getResults() {
        return results;
    }

}
