package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining a key-value property to be included in the result of a diff mapping.
 * <p>
 * This annotation is intended to be used within other annotations (such as {@link DiffMapping})
 * to attach additional metadata properties to a diff result.
 * </p>
 *
 * <pre>
 *     &#64;DiffMapping(
 *         value = "fieldName",
 *         properties = {
 *             &#64;DiffProperty(key = "description", value = "Primary key"),
 *             &#64;DiffProperty(key = "sensitive", value = "true")
 *         }
 *     )
 * </pre>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface DiffProperty {

    /**
     * The identifier for the property.
     *
     * @return the property key
     */
    String key();

    /**
     * The value of the property.
     *
     * @return the property value
     */
    String value();

}