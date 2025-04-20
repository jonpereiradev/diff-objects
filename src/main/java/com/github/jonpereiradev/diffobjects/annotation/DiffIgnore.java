package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to ignore a specific mapping when {@link DiffMapping} is applied to a class that maps all fields.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DiffIgnore {
}
