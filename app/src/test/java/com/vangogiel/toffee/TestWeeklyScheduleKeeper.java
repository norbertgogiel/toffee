package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestWeeklyScheduleKeeper {

  @Mock private WeekdayAnnotationProcessor mockWeekdayAnnotationProcessor;
  @Mock private IntervalScheduledTaskProcessor mockTaskProcessor;
  @Mock private Runnable mockRunnable;
  @Mock private IntervalScheduledTime mockTime;
  private final Set<DayOfWeek> setDays = new HashSet<>();
  private WeeklyScheduleKeeper subject;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    subject = new WeeklyScheduleKeeper(mockWeekdayAnnotationProcessor, mockTaskProcessor);
  }

  @Test
  public void testPlanInMondayTask() throws NoSuchMethodException {
    prepareAnnotationTaskProcessor(DayOfWeek.MONDAY);
    prepareTaskProcessor();
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testMonday"));
    assertWeeklyEntries(1, 0, 0, 0, 0, 0, 0);
  }

  @Test
  public void testPlanInMixedDays() throws NoSuchMethodException {
    prepareAnnotationTaskProcessor(DayOfWeek.MONDAY, DayOfWeek.SATURDAY);
    prepareTaskProcessor();
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testMixedDays"));
    assertWeeklyEntries(1, 0, 0, 0, 0, 1, 0);
  }

  @Test
  public void testAllDaysOfWeek() throws NoSuchMethodException {
    prepareAnnotationTaskProcessor(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY);
    prepareTaskProcessor();
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testAllDaysOfWeek"));
    assertWeeklyEntries(1, 1, 1, 1, 1, 1, 1);
  }

  static class WeeklySchedules {

    @Weekdays(days = "Mon")
    public void testMonday() {}

    @Weekdays(days = "Mon,Sat")
    public void testMixedDays() {}

    public void testAllDaysOfWeek() {}
  }

  private void prepareAnnotationTaskProcessor(DayOfWeek... dayOfWeeks) {
    setDays.addAll(Arrays.asList(dayOfWeeks));
    Mockito.when(mockWeekdayAnnotationProcessor.process(Mockito.any())).thenReturn(setDays);
  }

  private void prepareTaskProcessor() {
    IntervalScheduledTask task =
        new IntervalScheduledTask(mockRunnable, mockTime, 0L, TimeUnit.SECONDS);
    Mockito.when(mockTaskProcessor.processRawAndWrap(Mockito.any(), Mockito.any()))
        .thenReturn(task);
  }

  private void assertWeeklyEntries(
      int monCount,
      int tueCount,
      int wedCount,
      int thuCount,
      int friCount,
      int satCount,
      int sunCount) {
    assertEquals(monCount, subject.getNumberOfTasksScheduled(DayOfWeek.MONDAY));
    assertEquals(tueCount, subject.getNumberOfTasksScheduled(DayOfWeek.TUESDAY));
    assertEquals(wedCount, subject.getNumberOfTasksScheduled(DayOfWeek.WEDNESDAY));
    assertEquals(thuCount, subject.getNumberOfTasksScheduled(DayOfWeek.THURSDAY));
    assertEquals(friCount, subject.getNumberOfTasksScheduled(DayOfWeek.FRIDAY));
    assertEquals(satCount, subject.getNumberOfTasksScheduled(DayOfWeek.SATURDAY));
    assertEquals(sunCount, subject.getNumberOfTasksScheduled(DayOfWeek.SUNDAY));
  }
}
