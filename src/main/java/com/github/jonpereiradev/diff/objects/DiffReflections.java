package com.github.jonpereiradev.diff.objects;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonpereiradev@gmail.com
 */
final class DiffReflections {

    private static final Map<String, List<DiffMetadata>> CACHE_MAP = new ConcurrentHashMap<String, List<DiffMetadata>>();

    /**
     * Descobre os mapeamentos de diff na classe.
     *
     * @param diffClass classe que será analisada.
     * @return metadata para realizar o diff.
     */
    public static List<DiffMetadata> discover(Class<?> diffClass, String group) {
        List<DiffMetadata> metadatas = new LinkedList<>();

        if (!CACHE_MAP.containsKey(diffClass.getName())) {
            try {
                for (Method method : diffClass.getMethods()) {
                    if (method.isAnnotationPresent(DiffProperty.class)) {
                        DiffProperty property = method.getAnnotation(DiffProperty.class);
                        DiffStrategy strategy = method.getAnnotation(DiffStrategy.class);

                        if (group == null || group.equals(property.group())) {
                            metadatas.add(createMetadata(method, property, strategy));
                        }
                    } else if (method.isAnnotationPresent(DiffProperties.class)) {
                        for (DiffProperty property : method.getAnnotation(DiffProperties.class).value()) {
                            if (group == null || group.equals(property.group())) {
                                metadatas.add(createMetadata(method, property, property.strategy()));
                            }
                        }
                    }
                }
            } catch (SecurityException ex) {
                Logger.getLogger(DiffObjects.class.getName()).log(Level.SEVERE, null, ex);
            }

            Collections.sort(metadatas);
            CACHE_MAP.put(diffClass.getName(), metadatas);
        }


        return CACHE_MAP.get(diffClass.getName());
    }

    public static Method discoverGetter(Object object, String property) {
        return discoverGetter(object.getClass(), property);
    }

    public static <T> T invoke(Method method, Object instance) {
        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DiffReflections.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DiffReflections.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DiffReflections.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null; // fail safe dummy
    }

    private static Method discoverGetter(Class<?> diffClass, String property) {
        try {
            String possibleAccessMethodName = "get" + StringUtils.capitalize(property);
            return diffClass.getMethod(possibleAccessMethodName);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DiffObjects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DiffObjects.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Registra uma propriedade de diff como metadata.
     *
     * @param method       método com a anotação.
     * @param diffProperty anotação declarada para o diff.
     * @param diffStrategy estratégia de análise do diff.
     */
    private static DiffMetadata createMetadata(
            Method method,
            DiffProperty diffProperty,
            DiffStrategy diffStrategy) {
        DiffMetadata metadata = new DiffMetadata();

        metadata.setAnnotation(diffProperty);
        metadata.setStrategy(diffStrategy);
        metadata.setMethod(method);

        if (method.isAnnotationPresent(DiffOrder.class)) {
            metadata.setOrder(method.getAnnotation(DiffOrder.class));
        }

        return metadata;
    }

}
