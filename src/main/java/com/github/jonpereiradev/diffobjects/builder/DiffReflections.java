package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Common reflections operations to execute the diff.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
public final class DiffReflections {

    /**
     * Map the methods of the object that has the annotations for diff and stores in cache.
     *
     * @param diffClass class that have the diff annotations.
     * @return the diff mappings of the class.
     */
    public static DiffConfiguration mapAnnotations(Class<?> diffClass) {
        DiffBuilder builder = DiffBuilder.map(diffClass);

        if (diffClass.isAnnotationPresent(DiffMappings.class)) {
            mapAllMethods(diffClass, builder);
        } else {
            mapAnnotationsMethods(diffClass, builder);
        }

        return new DiffConfigurationImpl(builder.getMetadatas());
    }

    /**
     * Discover the public non-args method for access a field value.
     *
     * @param diffClass the class that has the getter method.
     * @param fieldOrMethodName the name of the field for discover or the name of the getter method.
     * @return the getter method to get the value.
     */
    public static Method discoverGetter(Class<?> diffClass, String fieldOrMethodName) {
        String possibleAccessMethodName = fieldOrMethodName;

        if (!fieldOrMethodName.startsWith("get")) {
            possibleAccessMethodName = "get" + StringUtils.capitalize(fieldOrMethodName);
        }

        Method method = MethodUtils.getMatchingAccessibleMethod(diffClass, possibleAccessMethodName, null);

        if (method == null) {
            throw new DiffException("Method " + possibleAccessMethodName + " not found in class " + diffClass.getName());
        }

        return method;
    }

    /**
     * Calls the method for the object and returns the value.
     *
     * @param instance the object instance that have the method.
     * @param method the getter method to get the value.
     * @param <T> the type of value returned by the method.
     * @return the value returned by the getter method.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object instance, Method method) {
        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DiffException("Method " + method.getName() + " must be public and no-args.");
        }
    }

    /**
     * Map all methods from a class for diff.
     *
     * @param diffClass the class that has the methods.
     * @param builder the builder that creates the metadata.
     */
    private static DiffConfiguration mapAllMethods(Class<?> diffClass, DiffBuilder builder) {
        for (Method method : diffClass.getMethods()) {
            builder.mapping(method.getName());
        }

        return new DiffConfigurationImpl(builder.getMetadatas());
    }

    /**
     * Map all method annotations from a class.
     *
     * @param diffClass the class that has the annotations.
     * @param builder the builder that creates the metadata.
     */
    private static DiffConfiguration mapAnnotationsMethods(Class<?> diffClass, DiffBuilder builder) {
        for (Method method : diffClass.getMethods()) {
            if (method.isAnnotationPresent(DiffMapping.class)) {
                DiffMapping diffMapping = method.getAnnotation(DiffMapping.class);
                DiffQueryMappingBuilder query = builder.mapping(method.getName(), diffMapping.value());

                for (DiffProperty diffProperty : diffMapping.properties()) {
                    query.property(diffProperty.key(), diffProperty.value());
                }
            } else if (method.isAnnotationPresent(DiffMappings.class)) {
                for (DiffMapping diffMapping : method.getAnnotation(DiffMappings.class).value()) {
                    builder.mapping(method.getName(), diffMapping.value());
                    DiffQueryMappingBuilder query = builder.mapping(method.getName(), diffMapping.value());

                    for (DiffProperty diffProperty : diffMapping.properties()) {
                        query.property(diffProperty.key(), diffProperty.value());
                    }
                }
            }
        }

        return new DiffConfigurationImpl(builder.getMetadatas());
    }
}