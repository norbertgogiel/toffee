package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.IllegalFormatPrecisionException;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/** Test class for {@link TimeParser}. */
public class TestTimeParser {

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testIncorrectTimeFormat() {
    TimeParser subject = new TimeParser();
    assertThrows(IllegalFormatPrecisionException.class, () -> subject.validateAndParse("01"));
  }

  @Test
  public void testHourLargerThanMax() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("24:59:59"));
  }

  @Test
  public void testHourLessThanZero() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("-01:59:59"));
  }

  @Test
  public void testMinuteLargerThanMax() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("23:60:59"));
  }

  @Test
  public void testMinuteLessThanZero() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("23:-01:59"));
  }

  @Test
  public void testSecondLargerThanMax() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("23:59:60"));
  }

  @Test
  public void testSecondLessThanZero() {
    TimeParser subject = new TimeParser();
    assertThrows(DateTimeException.class, () -> subject.validateAndParse("23:59:-01"));
  }

  @Test
  public void testNaNTimeFormat() {
    TimeParser subject = new TimeParser();
    assertThrows(NumberFormatException.class, () -> subject.validateAndParse("aa:aa:aa"));
  }

  @Test
  public void testCorrectTime() {
    TimeParser subject = new TimeParser();
    LocalTime timeUnderTest = LocalTime.of(11, 11, 11);
    AtomicReference<LocalTime> localTime = new AtomicReference<>();
    assertDoesNotThrow(() -> localTime.set(subject.validateAndParse("11:11:11")));
    assertEquals(0, localTime.get().compareTo(timeUnderTest));
  }
}
