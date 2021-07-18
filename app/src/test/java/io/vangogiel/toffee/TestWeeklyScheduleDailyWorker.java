package io.vangogiel.toffee;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** Test class for {@link WeeklyScheduleDailyWorker} */
public class TestWeeklyScheduleDailyWorker {

  @Mock private LocalDateTimeService localDateTimeService;
  @Mock private IntervalScheduledTaskProcessor taskProcessor;
  @Mock private WeeklyScheduleKeeper mockWeeklyScheduleKeeper;
  @Mock private IntervalScheduledTask mockTask1;
  @Mock private IntervalScheduledTask mockTask2;
  private final Map<DayOfWeek, List<IntervalScheduledTask>> schedule = new HashMap<>();
  private final List<IntervalScheduledTask> tasksForTheDay = new ArrayList<>();
  private WeeklyScheduleDailyWorker subject;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    schedule.clear();
    tasksForTheDay.clear();
    subject =
        new WeeklyScheduleDailyWorker(
            mockWeeklyScheduleKeeper, localDateTimeService, taskProcessor);
    prepareTodayIsMonday();
  }

  @Test
  public void testScheduleTasksForToday() throws InterruptedException {
    prepareTime(0, 0, 1);
    tasksForTheDay.add(mockTask1);
    tasksForTheDay.add(mockTask2);
    Mockito.when(mockWeeklyScheduleKeeper.getTaskScheduled(DayOfWeek.MONDAY))
        .thenReturn(tasksForTheDay);

    subject.run();

    Thread.sleep(1500);

    Mockito.verify(taskProcessor, Mockito.times(1)).schedule(mockTask1);
    Mockito.verify(taskProcessor, Mockito.times(1)).schedule(mockTask2);
  }

  @Test
  public void testVerifyNoTasksForToday() {
    prepareTime(0, 0, 1);
    tasksForTheDay.add(mockTask1);
    Mockito.when(mockWeeklyScheduleKeeper.getTaskScheduled(DayOfWeek.TUESDAY))
        .thenReturn(tasksForTheDay);

    subject.run();

    Mockito.verifyZeroInteractions(taskProcessor);
  }

  @Test
  public void testVerifyInvokedBeforeMidnightAndAfter() throws InterruptedException {
    prepareTime(23, 59, 59);
    tasksForTheDay.add(mockTask1);
    Mockito.when(mockWeeklyScheduleKeeper.getTaskScheduled(DayOfWeek.MONDAY))
        .thenReturn(tasksForTheDay);

    subject.run();

    Thread.sleep(1500);

    Mockito.verify(taskProcessor, Mockito.times(2)).schedule(mockTask1);
  }

  private void prepareTime(int hour, int minute, int second) {
    Mockito.when(localDateTimeService.timeNow()).thenReturn(LocalTime.of(hour, minute, second));
  }

  private void prepareTodayIsMonday() {
    Mockito.when(localDateTimeService.dateNow()).thenReturn(LocalDate.of(2021, 6, 14));
  }
}
