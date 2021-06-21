package com.vangogiel.toffee;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for scheduling tasks for the day.
 *
 * <p>The main overridden task checks the day of the week for the day it is running on. Then, it
 * retrieves the tasks from the map provided.
 *
 * <p>All the tasks for the day are scheduled using {@link
 * com.vangogiel.toffee.IntervalScheduledTaskProcessor}.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see com.vangogiel.toffee.IntervalScheduledTaskProcessor
 */
public class WeeklyScheduleDailyWorker implements Runnable {

  private final Map<DayOfWeek, List<IntervalScheduledTask>> schedule;
  private final LocalDateTimeService localDateTimeService;
  private final IntervalScheduledTaskProcessor taskProcessor;

  /**
   * Create new DailyScheduleWorker.
   *
   * @param schedule of all the tasks for for the week
   * @param localDateTimeService wrapping up {@code LocalDateTime}
   * @param taskProcessor allowing to schedule tasks
   */
  public WeeklyScheduleDailyWorker(
      Map<DayOfWeek, List<IntervalScheduledTask>> schedule,
      LocalDateTimeService localDateTimeService,
      IntervalScheduledTaskProcessor taskProcessor) {
    this.schedule = schedule;
    this.localDateTimeService = localDateTimeService;
    this.taskProcessor = taskProcessor;
  }

  @Override
  public void run() {
    DayOfWeek dayOfWeekNow = localDateTimeService.dateNow().getDayOfWeek();
    List<IntervalScheduledTask> listOfTasks =
        schedule.getOrDefault(dayOfWeekNow, new ArrayList<>());
    listOfTasks.forEach(taskProcessor::schedule);
  }
}
