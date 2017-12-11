package com.github.jonpereiradev.diffobjects.annotation;

import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jonpereiradev@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DiffStrategy {

    DiffStrategyType type() default DiffStrategyType.SINGLE;

}
