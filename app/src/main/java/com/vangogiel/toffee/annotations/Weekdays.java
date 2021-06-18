package com.vangogiel.toffee.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation listing days of the week.
 *
 * <p>The days of the week are going to be used for scheduling tasks to run on those days of the
 * week.
 *
 * <p>Here are examples of how the annotation can be used to list days of the week:
 *
 * <ul>
 *   <li>list one day of the week: @Weekdays(days = "Mon")
 * </ul>
 *
 * @author Norbert Gogiel
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Weekdays {

  String days() default "";
}
