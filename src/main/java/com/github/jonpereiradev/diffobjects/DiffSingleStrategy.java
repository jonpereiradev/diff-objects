package com.github.jonpereiradev.diffobjects;

/**
 * Responsible by check the difference between two simple objects and generate an object with the difference.
 *
 * @author jonpereiradev@gmail.com
 */
public class DiffSingleStrategy implements DiffStrategable {

    @Override
    public <T> DiffObject<T> diff(Diff annotation, T before, T after) {
        boolean isEquals = before == after || before != null && before.equals(after);
        return isEquals ? null : new DiffObject<>(annotation, before, after);
    }
}
