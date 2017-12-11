package com.github.jonpereiradev.diffobjects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>A property or object annotated with this will be checked for difference between two objects.</p>
 * <p>When annotated on a object, all the properties will be checked for difference.</p>
 * <p>When annotated on a property, this property will be checked for difference.</p>
 *
 * @author jonpereiradev@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Diff {

    String value() default "";

}
