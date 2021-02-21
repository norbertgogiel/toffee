package com.norbertgogiel.toffee.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledUntil {

    int hour();
    int minute() default 0;
    int second() default 0;
    int nano() default 0;
}
