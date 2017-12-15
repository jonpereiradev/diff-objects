package com.github.jonpereiradev.diffobjects;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResults {

    private final List<DiffResult> results = new LinkedList<>();

    public List<DiffResult> getResults() {
        return results;
    }

}
