package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation for grouping multiple {@link DiffMapping} annotations on a single element.
 * <p>
 * This annotation enables the use of multiple {@code @DiffMapping} annotations on the same class,
 * field, or method. When applied to a class, it can also enable mapping for all methods within the class.
 * </p>
 *
 * <pre>
 *     &#64;DiffMappings({
 *         &#64;DiffMapping("id"),
 *         &#64;DiffMapping(value = "address.city", comparator = CityComparator.class)
 *     })
 *     public class MyClass { ... }
 * </pre>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DiffMappings {

    /**
     * Returns the array of {@link DiffMapping} annotations grouped by this container.
     * <p>
     * Allows performing multiple difference checks on a single method, field, or class.
     * </p>
     *
     * @return the group of {@link DiffMapping} annotations
     */
    DiffMapping[] value() default {};

}