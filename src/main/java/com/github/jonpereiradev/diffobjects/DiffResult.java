package com.github.jonpereiradev.diffobjects;

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
