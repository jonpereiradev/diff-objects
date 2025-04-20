package com.github.jonpereiradev.diffobjects.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Specifies the evaluation order for the method, if ordering is required.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DiffOrder {

    /**
     * @return the numerical order assigned to the method
     */
    int value();

}
