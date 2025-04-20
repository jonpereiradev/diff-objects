package com.github.jonpereiradev.diffobjects.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Used to group multiple {@link DiffMapping} annotations on a method, or to enable mapping for all methods when applied to a class.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DiffMappings {

    /**
     * Returns a group of mappings that allow performing multiple difference checks on a single method.
     *
     * @return the group of mappings
     */
    DiffMapping[] value() default {};

}
