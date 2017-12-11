package com.github.jonpereiradev.diffobjects;

import java.util.Collection;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffObject<T> {

    private T before;
    private T after;

    public DiffObject(Diff annotation, T before, T after) {
        this.before = before;
        this.after = after;
    }

    public T getBefore() {
        return before;
    }

    public T getAfter() {
        return after;
    }

    public boolean isCollection() {
        return before instanceof Collection || after instanceof Collection;
    }
}
