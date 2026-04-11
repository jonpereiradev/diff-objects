package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class providing common reflection operations used during diff execution.
 * <p>
 * Includes methods for discovering getter methods, invoking methods, and instantiating objects
 * via reflection. Used internally to access and manipulate object properties for diff operations.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffReflections {

    private static final String GET_METHOD_PREFIX = "get";

    /**
     * Discovers the public no-argument getter method for a given field or method name in the specified class.
     * <p>
     * If the provided name does not start with "get", it prepends "get" and capitalizes the first letter.
     * Throws a {@link DiffException} if the method cannot be found or is not public and no-args.
     * </p>
     *
     * @param diffClass the class containing the getter method
     * @param fieldOrMethodName the name of the field or the getter method
     *
     * @return the discovered getter {@link Method}
     *
     * @throws DiffException if the method is not found or is not public and no-args
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

    /**
     * Capitalizes the first letter of the provided field or method name.
     *
     * @param fieldOrMethodName the name to capitalize
     *
     * @return the capitalized name
     */
    private static String capitalize(String fieldOrMethodName) {
        char firstLetter = fieldOrMethodName.charAt(0);
        return Character.toUpperCase(firstLetter) + fieldOrMethodName.substring(1);
    }

    /**
     * Attempts to retrieve a public no-argument method by name from the specified class.
     * Returns {@code null} if the method is not found.
     *
     * @param diffClass the class to search for the method
     * @param methodName the name of the method to retrieve
     *
     * @return the {@link Method} if found, or {@code null} otherwise
     */
    private static Method quietlyGetMethod(Class<?> diffClass, String methodName) {
        try {
            return diffClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Invokes the specified method on the given object instance and returns the result.
     * <p>
     * Returns {@code null} if the instance is {@code null}. Throws a {@link DiffException}
     * if the method cannot be accessed or invoked.
     * </p>
     *
     * @param instance the object instance containing the method
     * @param method the method to invoke
     * @param <T> the type of the value returned by the method
     *
     * @return the value returned by the invoked method, or {@code null} if instance is {@code null}
     *
     * @throws DiffException if the method cannot be accessed or invoked
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
     * Creates a new instance of the specified class using its default constructor.
     *
     * @param clazz the class to instantiate
     * @param <T> the type of the object to be created
     *
     * @return a new instance of the specified class
     *
     * @throws UnsupportedOperationException if the class does not have a default constructor
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}