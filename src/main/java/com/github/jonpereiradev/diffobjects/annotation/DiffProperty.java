package com.github.jonpereiradev.diffobjects.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Maps a property to be included in the result of the mapping.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface DiffProperty {

    /**
     * Returns the identifier for the property.
     *
     * @return the property identifier
     */
    String key();

    /**
     * Returns the value of the property.
     *
     * @return the property value
     */
    String value();

}
