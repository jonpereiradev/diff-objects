package com.github.jonpereiradev.diffobjects.annotation;

import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field or method should be checked for differences between two objects during a diff operation.
 * <p>
 * This annotation can be applied to types, fields, or methods to specify that the annotated element
 * should be included in the diff comparison. When applied to a method without a {@code value()}, the method's
 * return value will be compared using its {@code equals()} method. When a {@code value()} is provided,
 * the specified property (which may be a nested property, e.g., {@code user.address.id}) will be evaluated for equality.
 * <p>
 * Additional properties can be attached to the diff result using the {@link #properties()} attribute.
 * A custom comparator can be specified via the {@link #comparator()} attribute; by default, {@link EqualsComparator} is used.
 * <p>
 * This annotation is repeatable via {@link DiffMappings}.
 *
 * <pre>
 *     &#64;DiffMapping("id")
 *     private Long id;
 *
 *     &#64;DiffMapping(value = "address.city", comparator = CityComparator.class)
 *     public Address getAddress() { ... }
 * </pre>
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
     * By default, {@link EqualsComparator} is used.
     *
     * @return the equality comparator
     */
    Class<? extends DiffComparator> comparator() default EqualsComparator.class;

}