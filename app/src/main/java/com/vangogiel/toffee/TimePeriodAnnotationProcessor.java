package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.EveryHour;
import com.vangogiel.toffee.annotations.EveryMinute;
import com.vangogiel.toffee.annotations.EverySecond;
import java.lang.reflect.Method;

/**
 * Processes a method annotated with one of the relevant schedules, to convert to seconds to the
 * caller.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see ToffeeContext
 */
public class TimePeriodAnnotationProcessor {

  /**
   * Processes a method annotated with one of the following:
   *
   * <ul>
   *   <li>{@link EverySecond}
   *   <li>{@link EveryMinute}
   *   <li>{@link EveryHour}
   *   <li>{@link Every}
   * </ul>
   *
   * <p>Each annotation {@code EverySecond}, {@code EveryMinute} and {@code @EveryHour} is interpreted
   * in seconds respectively.
   *
   * <p>An alternative annotation {@link Every} could be used to define a tailored period.
   *
   * @param method annotated with one of the period annotations
   * @return the period as {@code long}
   */
  public long process(Method method) {
    if (method.isAnnotationPresent(EverySecond.class)) {
      return 1;
    } else if (method.isAnnotationPresent(EveryMinute.class)) {
      return 60;
    } else if (method.isAnnotationPresent(EveryHour.class)) {
      return 3600;
    } else if (method.isAnnotationPresent(Every.class)) {
      Every every = method.getDeclaredAnnotation(Every.class);
      int period = every.period();
      return every.timeUnit().toSeconds(period);
    }
    return 1;
  }
}
