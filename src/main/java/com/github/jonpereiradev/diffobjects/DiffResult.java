package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.annotation.Diff;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResult<T> {

    private T before;
    private T after;
    private boolean equals;

    public DiffResult(T before, T after, boolean equals) {
        this.before = before;
        this.after = after;
        this.equals = equals;
    }

    public DiffResult(Diff annotation, T before, T after) {
        this.before = before;
        this.after = after;
    }

    public T getBefore() {
        return before;
    }

    public T getAfter() {
        return after;
    }

    public boolean isEquals() {
        return equals;
    }
}
