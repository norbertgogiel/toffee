package com.vangogiel.toffee;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.IllegalFormatPrecisionException;

/**
 * Time parser allowing to convert time provided as {@code String} and return it as {@link
 * LocalTime}.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 */
public class TimeParser {

  /**
   * Parses and validates time provided as {@code String} and wraps it up as {@link LocalTime}.
   *
   * <p>Consumes time in the format of "HH:mm:ss". Parses the time into three separate variables of
   * hour, minute and second. It then checks each is within a correct time range relative to its
   * type and wraps them up in a single {@code LocalTime}.
   *
   * <p>Exceptions related to incorrect time format will be propagated to the caller as {@link
   * IllegalFormatPrecisionException}. Exceptions due to hour, minute or second being out of its
   * range will be propagated back to caller as {@link DateTimeException}.
   *
   * @param time as {@code String} in format of "HH:mm:ss"
   * @return the time as {@link LocalTime}
   * @throws IllegalFormatPrecisionException if time format is incorrect
   * @throws DateTimeException if any of the time is out of its range
   */
  public LocalTime validateAndParse(String time) {
    String[] timeArr = time.split(":");
    if (timeArr.length != 3) {
      throw new IllegalFormatPrecisionException(timeArr.length);
    }
    int hour = Integer.parseInt(timeArr[0]);
    int minute = Integer.parseInt(timeArr[1]);
    int second = Integer.parseInt(timeArr[2]);
    if (hour < 0 || hour > 23) {
      throw new DateTimeException(formatMessage("hour (0 - 23)", hour));
    }
    if (minute < 0 || minute > 59) {
      throw new DateTimeException(formatMessage("minute (0 - 59)", minute));
    }
    if (second < 0 || second > 59) {
      throw new DateTimeException(formatMessage("second (0 - 59)", second));
    }
    return LocalTime.of(hour, minute, second);
  }

  /**
   * Allows to concat a message for exceptions relating to {@link DateTimeException}.
   *
   * @param message describing limits within which the value should be
   * @param actualValue provided by the caller
   * @return formatted exception message
   */
  private String formatMessage(String message, int actualValue) {
    return "Invalid value for "
        .concat(message)
        .concat(". Actual value: ")
        .concat(String.valueOf(actualValue));
  }
}
