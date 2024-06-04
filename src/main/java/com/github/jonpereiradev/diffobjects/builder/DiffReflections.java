package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Common reflections operations to execute the diff.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffReflections {

    private static final String GET_METHOD_PREFIX = "get";

    /**
     * Discover the public non-args method for access a field value.
     *
     * @param diffClass         the class that has the getter method.
     * @param fieldOrMethodName the name of the field for discover or the name of the getter method.
     * @return the getter method to get the value.
     */
    public static Method discoverGetter(Class<?> diffClass, String fieldOrMethodName) {
        String possibleAccessMethodName = fieldOrMethodName;

        if (!fieldOrMethodName.startsWith(GET_METHOD_PREFIX)) {
            possibleAccessMethodName = GET_METHOD_PREFIX + capitalize(fieldOrMethodName);
        }

        Method method = quietlyGetMethod(diffClass, possibleAccessMethodName);

        if (method == null) {
            throw new DiffException("Method " + possibleAccessMethodName + " not found or is not public and non-args in class " + diffClass.getName());
        }

        return method;
    }

    private static String capitalize(String fieldOrMethodName) {
        char firstLetter = fieldOrMethodName.charAt(0);
        return Character.toUpperCase(firstLetter) + fieldOrMethodName.substring(1);
    }

    private static Method quietlyGetMethod(Class<?> diffClass, String methodName) {
        try {
            return diffClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Calls the method for the object and returns the value.
     *
     * @param instance the object instance that have the method.
     * @param method   the getter method to get the value.
     * @param <T>      the type of value returned by the method.
     * @return the value returned by the getter method.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object instance, Method method) {
        if (instance == null) {
            return null;
        }

        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DiffException("Method " + method.getName() + " must be public and no-args.");
        }
    }

    /**
     * Create a class instance.
     *
     * @param clazz class that will be used for create an instance.
     * @param <T>   object type that will be returned by the class.
     * @return created object.
     * @throws UnsupportedOperationException if no default constructor exists.
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
