package io.vangogiel.toffee.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotation to specify a period at which a method will to be scheduled.
 *
 * <p>Allows to specify customised {@code period} by specifying period as {@code int} and {@code
 * timeUnit} using {@link TimeUnit}, e.g.
 *
 * <ul>
 *   <li>@Every(period=5, timeUnit=TimeUnit.SECONDS)
 *   <li>@Every(period=3, timeUnit=TimeUnit.MINUTES)
 *   <li>@Every(period=2, timeUnit=TimeUnit.HOURS)
 * </ul>
 *
 * <p>Processing of the annotation is performed by {@link
 * io.vangogiel.toffee.TimePeriodAnnotationProcessor}.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see io.vangogiel.toffee.TimePeriodAnnotationProcessor
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Every {

  /**
   * Time period to schedule a method at, defined as {@code int} that will correspond to the time
   * unit provided.
   *
   * @return time period as {@code int}
   */
  int period() default 0;

  /**
   * Time unit for the period specified. This is to be provided by calling {@code TimeUnit}.
   *
   * @return time unit as {@code TimeUnit}
   */
  TimeUnit timeUnit() default TimeUnit.SECONDS;
}
