package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;
import com.github.jonpereiradev.diffobjects.builder.DiffBuilder;
import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import com.github.jonpereiradev.diffobjects.builder.DiffInstanceBuilder;
import com.github.jonpereiradev.diffobjects.builder.DiffQueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class for common reflections operations.
 *
 * @author jonpereiradev@gmail.com
 */
public final class DiffReflections {

    private static final Map<String, DiffConfiguration> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * Map the methods of the object that has the annotations for diff and stores in cache.
     *
     * @param diffClass class that have the diff annotations.
     * @return the diff mappings of the class.
     */
    public static DiffConfiguration mapAnnotations(Class<?> diffClass) {
        if (!CACHE_MAP.containsKey(diffClass.getName())) {
            DiffInstanceBuilder builder = DiffBuilder.map(diffClass);

            for (Method method : diffClass.getMethods()) {
                if (method.isAnnotationPresent(DiffMapping.class)) {
                    DiffMapping diffMapping = method.getAnnotation(DiffMapping.class);
                    DiffQueryBuilder query = builder.mapper().mapping(method.getName(), diffMapping.value());

                    for (DiffProperty diffProperty : diffMapping.properties()) {
                        query.property(diffProperty.key(), diffProperty.value());
                    }
                } else if (method.isAnnotationPresent(DiffMappings.class)) {
                    for (DiffMapping diffMapping : method.getAnnotation(DiffMappings.class).value()) {
                        builder.mapper().mapping(method.getName(), diffMapping.value());
                        DiffQueryBuilder query = builder.mapper().mapping(method.getName(), diffMapping.value());

                        for (DiffProperty diffProperty : diffMapping.properties()) {
                            query.property(diffProperty.key(), diffProperty.value());
                        }
                    }
                }
            }

            CACHE_MAP.put(diffClass.getName(), builder.configuration());
        }

        return CACHE_MAP.get(diffClass.getName());
    }

    /**
     * Discover the public non-args method for access a field value.
     *
     * @param diffClass         the class that has the getter method.
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
     * @param method   the getter method to get the value.
     * @param <T>      the type of value returned by the method.
     * @return the value returned by the getter method.
     */
    public static <T> T invoke(Object instance, Method method) {
        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DiffException("Method " + method.getName() + " must be public and no-args.");
        }
    }
}
