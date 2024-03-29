package com.github.jonpereiradev.diffobjects.annotation;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * A field or method annotated with this will be checked for difference between two objects.
 * When annotated on a method without value(), the equals method of this object will be executed.
 * When annotated on a method with value(), the property will be evaluated for equality.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Repeatable(DiffMappings.class)
public @interface DiffMapping {

    /**
     * Defines the property that will be evaluated for equality.
     * It can be nested property like user.address.id.
     * 
     * @return path to the property
     */
    String value() default "";

    /**
     * @return additional properties that will be on the diff result object.
     */
    DiffProperty[] properties() default {};

    /**
     * @return define the comparator that checks the equality of two objects.
     */
    Class<? extends DiffComparator> comparator() default EqualsComparator.class;

}
