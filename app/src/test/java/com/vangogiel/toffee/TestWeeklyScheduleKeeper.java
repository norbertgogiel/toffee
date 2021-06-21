package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
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

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testPlanInMondayTask() throws NoSuchMethodException {
    setDays.add(DayOfWeek.MONDAY);
    IntervalScheduledTask task =
        new IntervalScheduledTask(mockRunnable, mockTime, 0L, TimeUnit.SECONDS);
    Mockito.when(mockWeekdayAnnotationProcessor.process(Mockito.any())).thenReturn(setDays);
    Mockito.when(mockTaskProcessor.processRawAndWrap(Mockito.any(), Mockito.any()))
        .thenReturn(task);
    WeeklyScheduleKeeper subject =
        new WeeklyScheduleKeeper(mockWeekdayAnnotationProcessor, mockTaskProcessor);
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testMonday"));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.MONDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.TUESDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.WEDNESDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.THURSDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.FRIDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.SATURDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.SUNDAY));
  }

  @Test
  public void testPlanInMixedDays() throws NoSuchMethodException {
    setDays.add(DayOfWeek.MONDAY);
    setDays.add(DayOfWeek.SATURDAY);
    IntervalScheduledTask task =
        new IntervalScheduledTask(mockRunnable, mockTime, 0L, TimeUnit.SECONDS);
    Mockito.when(mockWeekdayAnnotationProcessor.process(Mockito.any())).thenReturn(setDays);
    Mockito.when(mockTaskProcessor.processRawAndWrap(Mockito.any(), Mockito.any()))
        .thenReturn(task);
    WeeklyScheduleKeeper subject =
        new WeeklyScheduleKeeper(mockWeekdayAnnotationProcessor, mockTaskProcessor);
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testMixedDays"));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.MONDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.TUESDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.WEDNESDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.THURSDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.FRIDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.SATURDAY));
    assertEquals(0, subject.getNumberOfTasksScheduled(DayOfWeek.SUNDAY));
  }

  @Test
  public void testAllDaysOfWeek() throws NoSuchMethodException {
    setDays.add(DayOfWeek.MONDAY);
    setDays.add(DayOfWeek.TUESDAY);
    setDays.add(DayOfWeek.WEDNESDAY);
    setDays.add(DayOfWeek.THURSDAY);
    setDays.add(DayOfWeek.FRIDAY);
    setDays.add(DayOfWeek.SATURDAY);
    setDays.add(DayOfWeek.SUNDAY);
    IntervalScheduledTask task =
        new IntervalScheduledTask(mockRunnable, mockTime, 0L, TimeUnit.SECONDS);
    Mockito.when(mockWeekdayAnnotationProcessor.process(Mockito.any())).thenReturn(setDays);
    Mockito.when(mockTaskProcessor.processRawAndWrap(Mockito.any(), Mockito.any()))
        .thenReturn(task);
    WeeklyScheduleKeeper subject =
        new WeeklyScheduleKeeper(mockWeekdayAnnotationProcessor, mockTaskProcessor);
    subject.planIn(WeeklySchedules.class, WeeklySchedules.class.getMethod("testAllDaysOfWeek"));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.MONDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.TUESDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.WEDNESDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.THURSDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.FRIDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.SATURDAY));
    assertEquals(1, subject.getNumberOfTasksScheduled(DayOfWeek.SUNDAY));
  }

  static class WeeklySchedules {

    @Weekdays(days = "Mon")
    public void testMonday() {}

    @Weekdays(days = "Mon,Sat")
    public void testMixedDays() {}

    public void testAllDaysOfWeek() {}
  }
}
