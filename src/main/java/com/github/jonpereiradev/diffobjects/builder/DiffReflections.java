package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.annotation.DiffIgnore;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


/**
 * Common reflections operations to execute the diff.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffReflections {

    /**
     * Map the methods of the object that has the annotations for diff and stores in cache.
     *
     * @param diffClass class that have the diff annotations.
     *
     * @return the diff mappings of the class.
     */
    public static DiffConfiguration mapAnnotations(Class<?> diffClass) {
        DiffBuilder<?> builder = DiffBuilder.map(diffClass);

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
     *
     * @return the getter method to get the value.
     */
    public static Method discoverGetter(Class<?> diffClass, String fieldOrMethodName) {
        String possibleAccessMethodName = fieldOrMethodName;

        if (!fieldOrMethodName.startsWith("get")) {
            possibleAccessMethodName = "get" + StringUtils.capitalize(fieldOrMethodName);
        }

        Method method = MethodUtils.getMatchingAccessibleMethod(diffClass, possibleAccessMethodName, null);

        if (method == null) {
            throw new DiffException("Method " + possibleAccessMethodName + " not found or is not public and non-args in class " + diffClass.getName());
        }

        return method;
    }

    /**
     * Calls the method for the object and returns the value.
     *
     * @param instance the object instance that have the method.
     * @param method the getter method to get the value.
     * @param <T> the type of value returned by the method.
     *
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
     * Map all methods from a class for diff.
     *
     * @param diffClass the class that has the methods.
     * @param builder the builder that creates the metadata.
     */
    private static DiffConfiguration mapAllMethods(Class<?> diffClass, DiffBuilder<?> builder) {
        for (Method method : diffClass.getMethods()) {
            if (!method.isAnnotationPresent(DiffIgnore.class)) {
                builder.mapping(method.getName());
            }
        }

        return new DiffConfigurationImpl(builder.getMetadatas());
    }

    /**
     * Map all method annotations from a class.
     *
     * @param diffClass the class that has the annotations.
     * @param builder the builder that creates the metadata.
     */
    private static DiffConfiguration mapAnnotationsMethods(Class<?> diffClass, DiffBuilder<?> builder) {
        for (Method method : diffClass.getMethods()) {
            if (method.isAnnotationPresent(DiffMapping.class)) {
                mapping(builder, method, method.getAnnotation(DiffMapping.class));
            } else if (method.isAnnotationPresent(DiffMappings.class)) {
                for (DiffMapping diffMapping : method.getAnnotation(DiffMappings.class).value()) {
                    mapping(builder, method, diffMapping);
                }
            }
        }

        return new DiffConfigurationImpl(builder.getMetadatas());
    }

    private static void mapping(DiffBuilder builder, Method method, DiffMapping diffMapping) {
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            mappingCollection(builder, method, diffMapping);
        } else {
            mappingField(builder, method, diffMapping);
        }
    }

    private static void mappingField(DiffBuilder builder, Method method, DiffMapping diffMapping) {
        String field = method.getName();
        DiffComparator comparator = DiffReflections.newInstance(diffMapping.comparator());

        if (!diffMapping.value().isEmpty()) {
            field += "." + diffMapping.value();
        }

        DiffQueryMappingBuilder query = builder.mapping(field, comparator);

        for (DiffProperty diffProperty : diffMapping.properties()) {
            query.property(diffProperty.key(), diffProperty.value());
        }
    }

    private static void mappingCollection(DiffBuilder builder, Method method, DiffMapping diffMapping) {
        DiffComparator comparator = DiffReflections.newInstance(diffMapping.comparator());
        DiffQueryMappingBuilder collectionBuilder = builder.mapping(method.getName(), comparator);

        if (!diffMapping.value().isEmpty()) {
            collectionBuilder.mapping(method.getName() + "." + diffMapping.value(), comparator);
        }
    }

    /**
     * Create a class instance.
     *
     * @param clazz class that will be used for create an instance.
     * @param <T> object type that will be returned by the class.
     *
     * @return created object.
     *
     * @throws UnsupportedOperationException if no default constructor exists.
     */
    private static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
