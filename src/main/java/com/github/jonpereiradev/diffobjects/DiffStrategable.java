package com.github.jonpereiradev.diffobjects;

import java.lang.reflect.Method;

/**
 * @author jonpereiradev@gmail.com
 */
public interface DiffStrategable {

    <T> DiffObject<T> diff(T before, T after, Method method);

}
