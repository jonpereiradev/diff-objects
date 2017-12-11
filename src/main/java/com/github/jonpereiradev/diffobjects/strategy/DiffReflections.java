package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.annotation.Diff;
import com.github.jonpereiradev.diffobjects.annotation.DiffGroup;
import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.annotation.DiffStrategy;
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
    public static List<DiffMetadata> discover(Class<?> diffClass) {
        List<DiffMetadata> metadatas = new LinkedList<>();

        if (!CACHE_MAP.containsKey(diffClass.getName())) {
            try {
                for (Method method : diffClass.getMethods()) {
                    if (method.isAnnotationPresent(Diff.class)) {
                        Diff property = method.getAnnotation(Diff.class);
                        DiffStrategy strategy = method.getAnnotation(DiffStrategy.class);

                        metadatas.add(createMetadata(method, property, strategy));
                    } else if (method.isAnnotationPresent(DiffGroup.class)) {
                        for (Diff property : method.getAnnotation(DiffGroup.class).value()) {
                            metadatas.add(createMetadata(method, property, null));
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
     * @param diff         anotação declarada para o diff.
     * @param diffStrategy estratégia de análise do diff.
     */
    private static DiffMetadata createMetadata(
            Method method,
            Diff diff,
            DiffStrategy diffStrategy) {
        DiffMetadata metadata = new DiffMetadata();

        metadata.setAnnotation(diff);
        metadata.setStrategy(diffStrategy);
        metadata.setMethod(method);

        if (method.isAnnotationPresent(DiffOrder.class)) {
            metadata.setOrder(method.getAnnotation(DiffOrder.class));
        }

        return metadata;
    }

}
