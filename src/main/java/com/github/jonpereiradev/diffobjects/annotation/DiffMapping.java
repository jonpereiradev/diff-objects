package com.github.jonpereiradev.diffobjects.annotation;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that a field or method should be checked for differences between two objects.
 * <p>
 * When applied to a method without a {@code value()}, the method's return value will be compared using its {@code equals()} method.
 * When applied to a method with a {@code value()}, the specified property will be evaluated for equality.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Repeatable(DiffMappings.class)
public @interface DiffMapping {

    /**
     * Defines the property to be evaluated for equality. Can be a nested property (e.g., {@code user.address.id}).
     *
     * @return the path to the property
     */
    String value() default "";

    /**
     * Returns additional properties that will be included in the result object.
     *
     * @return the additional properties
     */
    DiffProperty[] properties() default {};

    /**
     * Returns the comparator used to determine whether two objects are equal.
     *
     * @return the equality comparator
     */
    Class<? extends DiffComparator> comparator() default EqualsComparator.class;

}
