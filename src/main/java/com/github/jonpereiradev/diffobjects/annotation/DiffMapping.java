package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>A method annotated with this will be checked for difference between two objects.</p>
 * <p>When annotated on a method without value(), the equals method of this object will be executed.</p>
 * <p>When annotated on a method with value(), the property will be evaluated for equality.</p>
 *
 * @author jonpereiradev@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DiffMapping {

    /**
     * @return Defines the property that will be evaluated for equality. It can be nested property like user.address.id.
     */
    String value() default "";

    /**
     * @return Aditional properties that will be on the diff result object.
     */
    DiffProperty[] properties() default {};
}
