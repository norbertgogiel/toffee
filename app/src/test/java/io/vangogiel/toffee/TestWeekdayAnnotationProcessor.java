package io.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** Test class for {@link WeekdayAnnotationProcessor} */
public class TestWeekdayAnnotationProcessor {

  private AnnotationProcessor<Weekdays, Set<DayOfWeek>> subject;

  @Before
  public void setUp() {
    subject = new WeekdayAnnotationProcessor();
  }

  @Test
  public void testProcessMonday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testMonday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.MONDAY);
  }

  @Test
  public void testProcessTuesday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testTuesday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.TUESDAY);
  }

  @Test
  public void testProcessWednesday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testWednesday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.WEDNESDAY);
  }

  @Test
  public void testProcessThursday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testThursday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.THURSDAY);
  }

  @Test
  public void testProcessFriday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testFriday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.FRIDAY);
  }

  @Test
  public void testProcessSaturday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testSaturday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.SATURDAY);
  }

  @Test
  public void testProcessSunday() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testSunday");
    assertEquals(1, result.size());
    assertWeekdays(result, DayOfWeek.SUNDAY);
  }

  @Test
  public void testMultipleRandomDaysOfTheWeek() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testMultipleDays");
    assertEquals(3, result.size());
    assertWeekdays(result, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
  }

  @Test
  public void testIncorrectMultipleRandomDaysOfTheWeek() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testMultipleIncorrectDays");
    assertEquals(0, result.size());
  }

  @Test
  public void testDaysOfTheWeekRange() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testDaysRange");
    assertEquals(5, result.size());
    assertWeekdays(
        result,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY);
  }

  @Test
  public void testDaysCombination() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testDaysCombination");
    assertEquals(6, result.size());
    assertWeekdays(
        result,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SUNDAY);
  }

  @Test
  public void testIncorrectDays() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testIncorrectDays");
    assertEquals(1, result.size());
    assertTrue(result.contains(DayOfWeek.SUNDAY));
  }

  @Test
  public void testErrorRange() throws NoSuchMethodException {
    Set<DayOfWeek> result = getResult("testErrorRange");
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

  private Set<DayOfWeek> getResult(String methodName) throws NoSuchMethodException {
    return subject.process(
        TestWeekdaysAnnotations.class.getMethod(methodName).getAnnotation(Weekdays.class));
  }

  private void assertWeekdays(Set<DayOfWeek> result, DayOfWeek... days) {
    Arrays.stream(days)
        .forEach(
            day -> {
              assertTrue(result.contains(day));
            });
  }
}
