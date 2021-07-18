package io.vangogiel.toffee;

import io.vangogiel.toffee.annotations.Every;
import io.vangogiel.toffee.annotations.EveryHour;
import io.vangogiel.toffee.annotations.EveryMinute;
import io.vangogiel.toffee.annotations.EverySecond;
import java.lang.reflect.Method;

/**
 * Processes a method annotated with one of the relevant schedules, to convert to seconds to the
 * caller.
 *
 * <p>Processes a method annotated with one of the following:
 *
 * <ul>
 *   <li>{@link io.vangogiel.toffee.annotations.EverySecond}
 *   <li>{@link EveryMinute}
 *   <li>{@link EveryHour}
 *   <li>{@link io.vangogiel.toffee.annotations.Every}
 * </ul>
 *
 * <p>Each annotation {@code EverySecond}, {@code EveryMinute} and {@code @EveryHour} is interpreted
 * in seconds respectively.
 *
 * <p>An alternative annotation {@link io.vangogiel.toffee.annotations.Every} could be used to
 * define a tailored period.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see ToffeeContext
 */
public class TimePeriodAnnotationProcessor implements AnnotationProcessor<Method, Long> {

  public Long process(Method method) {
    if (method.isAnnotationPresent(EverySecond.class)) {
      return 1L;
    } else if (method.isAnnotationPresent(EveryMinute.class)) {
      return 60L;
    } else if (method.isAnnotationPresent(EveryHour.class)) {
      return 3600L;
    } else if (method.isAnnotationPresent(Every.class)) {
      Every every = method.getDeclaredAnnotation(Every.class);
      int period = every.period();
      return every.timeUnit().toSeconds(period);
    }
    return 1L;
  }
}
