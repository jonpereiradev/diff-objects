package com.github.jonpereiradev.diffobjects;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResults {

    private final List<DiffResult> results = new LinkedList<DiffResult>();

    public List<DiffResult> getResults() {
        return results;
    }

}
