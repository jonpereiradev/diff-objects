package com.github.jonpereiradev.diffobjects.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to group multiple mappings on a method or to enable all methods if is used on a class.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DiffMappings {

    /**
     * @return a group of mappings to make multiple diffs on a single method.
     */
    DiffMapping[] value() default {};

}
