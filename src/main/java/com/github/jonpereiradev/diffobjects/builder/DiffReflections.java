package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

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
    private static final String IS_METHOD_PREFIX = "is";

    // Prevent instantiation
    private DiffReflections() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Discovers the public no-argument getter method for a given field or method name in the specified class.
     * <p>
     * If the provided name does not start with "get" or "is", it prepends "get" (and capitalizes the first letter),
     * and if not found, falls back to prepending "is".
     * Throws a {@link DiffException} if the method cannot be found or is not public and no-args.
     * </p>
     *
     * @param diffClass the class containing the getter method (must not be null)
     * @param fieldOrMethodName the name of the field or the getter method (must not be null or empty)
     *
     * @return the discovered getter {@link Method}
     *
     * @throws DiffException if the method is not found or is not public and no-args
     */
    public static Method discoverGetter(Class<?> diffClass, String fieldOrMethodName) {
        Objects.requireNonNull(diffClass, "diffClass must not be null");

        if (fieldOrMethodName == null || fieldOrMethodName.isEmpty()) {
            throw new IllegalArgumentException("fieldOrMethodName must not be null or empty");
        }

        Method method;

        if (fieldOrMethodName.startsWith(GET_METHOD_PREFIX) || fieldOrMethodName.startsWith(IS_METHOD_PREFIX)) {
            method = quietlyGetMethod(diffClass, fieldOrMethodName);
        } else {
            String getMethodName = GET_METHOD_PREFIX + capitalize(fieldOrMethodName);
            method = quietlyGetMethod(diffClass, getMethodName);

            if (method == null) {
                String isMethodName = IS_METHOD_PREFIX + capitalize(fieldOrMethodName);
                method = quietlyGetMethod(diffClass, isMethodName);
            }
        }

        if (method == null) {
            throw new DiffException("Method for property \"" + fieldOrMethodName + "\" not found or is not public and no-args in class " + diffClass.getName());
        }

        return method;
    }

    /**
     * Capitalizes the first letter of the provided field or method name.
     *
     * @param fieldOrMethodName the name to capitalize (must not be null or empty)
     *
     * @return the capitalized name
     */
    private static String capitalize(String fieldOrMethodName) {
        if (fieldOrMethodName == null || fieldOrMethodName.isEmpty()) {
            throw new IllegalArgumentException("fieldOrMethodName must not be null or empty");
        }

        if (fieldOrMethodName.length() == 1) {
            return fieldOrMethodName.toUpperCase();
        }

        char firstLetter = fieldOrMethodName.charAt(0);
        return Character.toUpperCase(firstLetter) + fieldOrMethodName.substring(1);
    }

    /**
     * Attempts to retrieve a public no-argument method by name from the specified class.
     * Returns {@code null} if the method is not found.
     *
     * @param diffClass the class to search for the method (must not be null)
     * @param methodName the name of the method to retrieve (must not be null or empty)
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
     * @param method the method to invoke (must not be null)
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
        if (method == null) {
            throw new IllegalArgumentException("method must not be null");
        }

        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException e) {
            throw new DiffException("Cannot access method " + method.getName() + ": " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DiffException("Illegal argument for method " + method.getName() + ": " + e.getMessage());
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw new DiffException("Exception thrown by method " + method.getName() + ": " + (cause != null ? cause.getMessage() : e.getMessage()));
        }
    }

    /**
     * Creates a new instance of the specified class using its default constructor.
     *
     * @param clazz the class to instantiate (must not be null)
     * @param <T> the type of the object to be created
     *
     * @return a new instance of the specified class
     *
     * @throws UnsupportedOperationException if the class does not have a default constructor or cannot be instantiated
     */
    public static <T> T newInstance(Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz must not be null");

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("No default constructor for class " + clazz.getName(), e);
        } catch (InstantiationException e) {
            throw new UnsupportedOperationException("Cannot instantiate class " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException("Cannot access default constructor of class " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw new UnsupportedOperationException("Constructor threw exception for class " + clazz.getName() + ": " + (cause != null ? cause.getMessage() : e.getMessage()), e);
        }
    }
}