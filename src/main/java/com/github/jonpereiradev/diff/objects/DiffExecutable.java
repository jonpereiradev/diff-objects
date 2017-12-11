package com.github.jonpereiradev.diff.objects;

/**
 * @author jonpereiradev@gmail.com
 */
public interface DiffExecutable {

    DiffObject diff(DiffProperty annotation, Object before, Object after);

}
