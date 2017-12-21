package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Maps a property on a mapping to be used on result.
 *
 * @author jonpereiradev@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface DiffProperty {

    /**
     * Identifier of the property.
     */
    String key();

    /**
     * Value of the property.
     */
    String value();

}
