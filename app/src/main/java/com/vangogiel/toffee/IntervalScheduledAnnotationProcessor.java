package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.lang.reflect.Method;
import java.time.LocalTime;

/**
 * Processes method annotations and it calculates the time to start and shutdown in form of delays
 * and encapsulates the data upon return to the caller.
 *
 * <p>The {@code String} is validated and parsed by {@link TimeParser}. The time is then converted
 * into a delay relative to the time the thread is running in. The server running will need to
 * calculate it into a delay from a perspective of when it is running.
 *
 * <p>The delay is also normalised upon comparison of {@code LocalTime.now()} and the time of that
 * particular schedule.
 *
 * <p>If the time now is greater than the time {@code ScheduledFrom}, but smaller than {@code
 * ScheduledUntil}, then the initial delay is set to 0 to start the task immediately. The delay to
 * shutdown will remain unchanged, in order to execute the task if started mid-schedule. This
 * scenario could occur if the host application or server crashed and the work has to be resumed.
 * Also, this scenario would apply if a restart of a sever is performed.
 *
 * <p>However, if the time now is smaller than the time {@code ScheduledFrom}, then the delay will
 * have a day added or it to start at that time the following day.
 *
 * <p>Throws {@code IllegalFormatPrecisionException} if the time in the annotation is of incorrect
 * format.
 *
 * <p>Throws {@code java.time.DateTimeException} if the time is out of its relative time bounds.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see ToffeeContext
 * @see ScheduledFrom
 * @see ScheduledUntil
 */
public class IntervalScheduledAnnotationProcessor
    implements AnnotationProcessor<Method, IntervalScheduledTime> {

  private final TimeParser timeParser;
  private final LocalDateTimeService localDateTimeService;

  /**
   * Create a new IntervalScheduledAnnotationProcessor.
   *
   * @param timeParser providing time parsing functionality
   * @param localDateTimeService providing wrapped {@code LocalDateTime}
   */
  public IntervalScheduledAnnotationProcessor(
      TimeParser timeParser, LocalDateTimeService localDateTimeService) {
    this.timeParser = timeParser;
    this.localDateTimeService = localDateTimeService;
  }

  public IntervalScheduledTime process(Method method) {
    ScheduledFrom rawFrom = method.getDeclaredAnnotation(ScheduledFrom.class);
    ScheduledUntil rawUntil = method.getDeclaredAnnotation(ScheduledUntil.class);
    LocalTime from = timeParser.validateAndParse(rawFrom.time());
    LocalTime until = timeParser.validateAndParse(rawUntil.time());
    long initDelay = calcRelativeToNow(from);
    long delayToShutdown = calcRelativeToNow(until);
    if (localDateTimeService.timeNow().compareTo(from) > 0) initDelay = 86400 + initDelay;
    if (localDateTimeService.timeNow().compareTo(until) < 0) {
      if (localDateTimeService.timeNow().compareTo(from) > 0) initDelay = 0;
    } else {
      delayToShutdown = 86400 + delayToShutdown;
    }
    return new IntervalScheduledTime(initDelay, delayToShutdown);
  }

  /**
   * Convert the given time into a delay to that time from {@link
   * com.vangogiel.toffee.LocalDateTimeService#timeNow()} ()}.
   *
   * @param time to calculate the delay from relative to now
   * @return delay from now to that time calculated as {@code long}
   */
  private long calcRelativeToNow(LocalTime time) {
    return time.toSecondOfDay() - localDateTimeService.timeNow().toSecondOfDay();
  }
}
