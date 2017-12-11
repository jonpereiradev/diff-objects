package com.github.jonpereiradev.diffobjects;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResults {

    private final List<DiffResult> results = new LinkedList<DiffResult>();

//    void diff(DiffMetadata metadata, Object before, Object after) {
//        DiffStrategy diffExecutable = DiffStrategyType.SINGLE.getDiffExecutable();
//
//        if (metadata.getStrategy() != null) {
//            diffExecutable = metadata.getStrategy().type().getDiffExecutable();
//        }
//
//        DiffResult diff = diffExecutable.diff(metadata.getAnnotation(), before, after);
//
//        if (diff != null) {
//            results.add(diff);
//        }
//    }

    public boolean isEquals() {
        return results.isEmpty();
    }

    public List<DiffResult> getResults() {
        return results;
    }

}
