package com.cegeka.project.infrastructure.debounce;

import java.lang.annotation.*;

/**
 * Debounces a method for a certain amount of milliseconds.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Debounce {

    long delay() default 400L;
}
