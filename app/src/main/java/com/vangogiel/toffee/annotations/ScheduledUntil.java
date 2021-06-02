package com.vangogiel.toffee.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation defining the time until which a method will be scheduled.
 *
 * <p>Allows to specify a scheduled until time for a potential scheduled candidate. The time format
 * should be of "HH:mm:ss", e.g.:
 *
 * <ul>
 *   <li>@ScheduledUntil(time="01:01:01")
 * </ul>
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see com.vangogiel.toffee.IntervalScheduledAnnotationProcessor
 * @see com.vangogiel.toffee.IntervalScheduledTaskProcessor
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledUntil {

  /**
   * Defines the time until in format of "HH:mm:ss" as {@code String}.
   *
   * @return the time as {@code String}
   */
  String time() default "";
}
