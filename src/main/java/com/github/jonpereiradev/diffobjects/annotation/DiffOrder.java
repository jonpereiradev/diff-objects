package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the evaluation order for a method during diff operations.
 * <p>
 * Use this annotation to assign a numerical order to methods when the order of evaluation
 * is significant in the diff process. Methods with lower values are evaluated before those
 * with higher values.
 * </p>
 *
 * <pre>
 *     &#64;DiffOrder(1)
 *     public String getFirstField() { ... }
 *
 *     &#64;DiffOrder(2)
 *     public String getSecondField() { ... }
 * </pre>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DiffOrder {

    /**
     * Returns the numerical order assigned to the method for evaluation.
     *
     * @return the order value
     */
    int value();

}