package com.github.jonpereiradev.diff.objects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jonpereiradev@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DiffProperty {

    String group() default "";

    String name() default "";

    String value() default "";

    DiffStrategy strategy() default @DiffStrategy(type = DiffStrategyType.DEEP_SEARCH);

}
