package io.vangogiel.toffee;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Wrapper class to provide dependency injection for {@link java.time.LocalDateTime}.
 *
 * @author Nobrert Gogiel
 * @since 1.0
 * @see ToffeeContext
 */
public class LocalDateTimeService {

  private final LocalDateTime localDateTime;

  /** Create default new LocalDateTimeService. */
  public LocalDateTimeService() {
    localDateTime = LocalDateTime.now();
  }

  /**
   * Create a new LocalDateTimeService with a custom clock.
   *
   * @param clock to be used for {@code LocalDateTime}
   */
  public LocalDateTimeService(Clock clock) {
    localDateTime = LocalDateTime.now(clock);
  }

  /**
   * Return time now from a specific clock from {@code LocalDateTime}.
   *
   * @return time now as {@code LocalTime}
   */
  public LocalTime timeNow() {
    return localDateTime.toLocalTime();
  }

  /**
   * Return date now from a specific clock from {@code LocalDateTime}.
   *
   * @return date now as {@code LocalDate}
   */
  public LocalDate dateNow() {
    return localDateTime.toLocalDate();
  }
}
