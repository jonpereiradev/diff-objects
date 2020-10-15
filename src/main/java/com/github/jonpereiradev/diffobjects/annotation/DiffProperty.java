package com.github.jonpereiradev.diffobjects.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Maps a property on a mapping to be used on result.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface DiffProperty {

    /**
     * @return identifier of the property.
     */
    String key();

    /**
     * @return value of the property.
     */
    String value();

}
