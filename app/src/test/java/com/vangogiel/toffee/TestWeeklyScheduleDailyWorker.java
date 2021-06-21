package com.vangogiel.toffee;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** Test class for {@link com.vangogiel.toffee.WeeklyScheduleDailyWorker} */
public class TestWeeklyScheduleDailyWorker {

  @Mock private LocalDateTimeService localDateTimeService;
  @Mock private IntervalScheduledTaskProcessor taskProcessor;
  @Mock private IntervalScheduledTask mockTask1;
  @Mock private IntervalScheduledTask mockTask2;
  private Map<DayOfWeek, List<IntervalScheduledTask>> schedule;
  private WeeklyScheduleDailyWorker subject;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    schedule = new HashMap<>();
    subject = new WeeklyScheduleDailyWorker(schedule, localDateTimeService, taskProcessor);
  }

  @Test
  public void testScheduleTasksForToday() {
    Mockito.when(localDateTimeService.dateNow()).thenReturn(LocalDate.of(2021, 6, 14));
    List<IntervalScheduledTask> tasksForTheDay = new ArrayList<>();
    tasksForTheDay.add(mockTask1);
    tasksForTheDay.add(mockTask2);
    schedule.put(DayOfWeek.MONDAY, tasksForTheDay);

    subject.run();

    Mockito.verify(taskProcessor).schedule(mockTask1);
    Mockito.verify(taskProcessor).schedule(mockTask2);
  }

  @Test
  public void testVerifyNoTasksForToday() {
    Mockito.when(localDateTimeService.dateNow()).thenReturn(LocalDate.of(2021, 6, 14));
    List<IntervalScheduledTask> tasksForTheDay = new ArrayList<>();
    tasksForTheDay.add(mockTask1);
    schedule.put(DayOfWeek.TUESDAY, tasksForTheDay);

    subject.run();

    Mockito.verifyZeroInteractions(taskProcessor);
  }
}
