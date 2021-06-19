package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
import java.util.Set;
import org.junit.Test;

/** Test class for {@link com.vangogiel.toffee.WeekdayAnnotationProcessor} */
public class TestWeekdayAnnotationProcessor {

  @Test
  public void testProcessMonday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testMonday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.MONDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessTuesday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testTuesday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.TUESDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessWednesday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testWednesday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.WEDNESDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessThursday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testThursday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.THURSDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessFriday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testFriday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.FRIDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessSaturday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testSaturday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.SATURDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testProcessSunday() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testSunday").getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.SUNDAY));
    assertEquals(1, result.size());
  }

  @Test
  public void testMultipleRandomDaysOfTheWeek() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class
                .getMethod("testMultipleDays")
                .getAnnotation(Weekdays.class));
    assertTrue(result.contains(DayOfWeek.MONDAY));
    assertTrue(result.contains(DayOfWeek.WEDNESDAY));
    assertTrue(result.contains(DayOfWeek.FRIDAY));
    assertEquals(3, result.size());
  }

  @Test
  public void testIncorrectMultipleRandomDaysOfTheWeek() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class
                .getMethod("testMultipleIncorrectDays")
                .getAnnotation(Weekdays.class));
    assertEquals(0, result.size());
  }

  @Test
  public void testDaysOfTheWeekRange() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class.getMethod("testDaysRange").getAnnotation(Weekdays.class));
    assertEquals(5, result.size());
    assertTrue(result.contains(DayOfWeek.MONDAY));
    assertTrue(result.contains(DayOfWeek.TUESDAY));
    assertTrue(result.contains(DayOfWeek.WEDNESDAY));
    assertTrue(result.contains(DayOfWeek.THURSDAY));
    assertTrue(result.contains(DayOfWeek.FRIDAY));
  }

  @Test
  public void testDaysCombination() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class
                .getMethod("testDaysCombination")
                .getAnnotation(Weekdays.class));
    assertEquals(6, result.size());
    assertTrue(result.contains(DayOfWeek.MONDAY));
    assertTrue(result.contains(DayOfWeek.TUESDAY));
    assertTrue(result.contains(DayOfWeek.WEDNESDAY));
    assertTrue(result.contains(DayOfWeek.THURSDAY));
    assertTrue(result.contains(DayOfWeek.FRIDAY));
    assertTrue(result.contains(DayOfWeek.SUNDAY));
  }

  @Test
  public void testIncorrectDays() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class
                .getMethod("testIncorrectDays")
                .getAnnotation(Weekdays.class));
    assertEquals(1, result.size());
    assertTrue(result.contains(DayOfWeek.SUNDAY));
  }

  @Test
  public void testErrorRange() throws NoSuchMethodException {
    WeekdayAnnotationProcessor subject = new WeekdayAnnotationProcessor();
    Set<DayOfWeek> result =
        subject.process(
            TestWeekdaysAnnotations.class
                .getMethod("testErrorRange")
                .getAnnotation(Weekdays.class));
    assertEquals(1, result.size());
    assertTrue(result.contains(DayOfWeek.SUNDAY));
  }

  static class TestWeekdaysAnnotations {

    @Weekdays(days = "Mon")
    public void testMonday() {}

    @Weekdays(days = "Tue")
    public void testTuesday() {}

    @Weekdays(days = "Wed")
    public void testWednesday() {}

    @Weekdays(days = "Thu")
    public void testThursday() {}

    @Weekdays(days = "Fri")
    public void testFriday() {}

    @Weekdays(days = "Sat")
    public void testSaturday() {}

    @Weekdays(days = "Sun")
    public void testSunday() {}

    @Weekdays(days = "Mon,Wed,Fri")
    public void testMultipleDays() {}

    @Weekdays(days = "Mon/Wed/Fri")
    public void testMultipleIncorrectDays() {}

    @Weekdays(days = "Mon-Fri")
    public void testDaysRange() {}

    @Weekdays(days = "Mon-Fri,Sun")
    public void testDaysCombination() {}

    @Weekdays(days = "Foo-Bar,Sun")
    public void testIncorrectDays() {}

    @Weekdays(days = "Mon-Bar,Sun")
    public void testErrorRange() {}
  }
}
