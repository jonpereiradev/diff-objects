package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffMappings;
import com.github.jonpereiradev.diffobjects.builder.DiffBuilder;
import com.github.jonpereiradev.diffobjects.builder.DiffInstanceBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonpereiradev@gmail.com
 */
public final class DiffReflections {

    private static final Map<String, List<DiffMetadata>> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * Descobre os mapeamentos de instance na classe.
     *
     * @param diffClass classe que será analisada.
     * @return metadata para realizar o instance.
     */
    public static List<DiffMetadata> mapAnnotations(Class<?> diffClass) {
        if (!CACHE_MAP.containsKey(diffClass.getName())) {
            DiffInstanceBuilder builder = DiffBuilder.map(diffClass);

            try {
                for (Method method : diffClass.getMethods()) {
                    if (method.isAnnotationPresent(DiffMapping.class)) {
                        DiffMapping property = method.getAnnotation(DiffMapping.class);
                        builder.mapper().mapping(method.getName(), property.value());
                    } else if (method.isAnnotationPresent(DiffMappings.class)) {
                        for (DiffMapping property : method.getAnnotation(DiffMappings.class).value()) {
                            builder.mapper().mapping(method.getName(), property.value());
                        }
                    }
                }
            } catch (SecurityException ex) {
                Logger.getLogger(DiffObjects.class.getName()).log(Level.SEVERE, null, ex);
            }

            CACHE_MAP.put(diffClass.getName(), builder.configuration().build());
        }


        return CACHE_MAP.get(diffClass.getName());
    }

    public static Method discoverGetter(Object object, String property) {
        return discoverGetter(object.getClass(), property);
    }

    public static <T> T invoke(Method method, Object instance) {
        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(DiffReflections.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null; // fail safe dummy
    }

    public static Method discoverGetter(Class<?> diffClass, String property) {
        String possibleAccessMethodName = property;

        if (!property.startsWith("get")) {
            possibleAccessMethodName = "get" + StringUtils.capitalize(property);
        }

        return MethodUtils.getMatchingAccessibleMethod(diffClass, possibleAccessMethodName, null);
    }

}
