package com.github.jonpereiradev.diffobjects.annotation;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;
import com.github.jonpereiradev.diffobjects.comparator.IndexComparator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Jonathan Pereira
 * @since 1.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DiffMappingCollection {

    /**
     * @return
     */
    DiffMapping[] value() default {};

    /**
     * @return define the comparator that checks that a object matches another in the collection for comparing.
     */
    Class<? extends DiffComparator> comparator() default IndexComparator.class;

}
