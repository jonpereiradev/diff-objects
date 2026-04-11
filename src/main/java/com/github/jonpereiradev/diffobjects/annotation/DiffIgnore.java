package com.github.jonpereiradev.diffobjects.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method should be ignored during diff operations when {@link DiffMapping}
 * is applied to a class that maps all fields.
 * <p>
 * Use this annotation on getter methods to exclude the corresponding property from
 * automatic diff mapping. This is useful when you want to prevent certain fields from
 * being included in the diff result, even if the class is configured to map all fields.
 * </p>
 *
 * <pre>
 *     public class MyClass {
 *         private String fieldA;
 *         private String fieldB;
 *
 *         public String getFieldA() { return fieldA; }
 *
 *         &#64;DiffIgnore
 *         public String getFieldB() { return fieldB; }
 *     }
 * </pre>
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DiffIgnore {
}