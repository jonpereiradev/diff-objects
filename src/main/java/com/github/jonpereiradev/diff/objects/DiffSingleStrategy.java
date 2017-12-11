package com.github.jonpereiradev.diff.objects;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffSingleStrategy implements DiffExecutable {

    @Override
    public DiffObject diff(DiffProperty annotation, Object before, Object after) {
        boolean isEquals = before == after || before != null && before.equals(after);
        return isEquals ? null : new DiffObject(annotation, before, after);
    }

}
