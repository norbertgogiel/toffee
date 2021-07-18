package io.vangogiel.toffee;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for scheduling tasks for the day.
 *
 * <p>The start method invokes two threads. One is to schedule the task now and the second one to
 * schedule the task at midnight.
 *
 * <p>The task checks the day of the week for the day it is running on. Then, it retrieves the list
 * of tasks from the map provided.
 *
 * <p>All the tasks for the day are scheduled using {@link IntervalScheduledTaskProcessor}.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 */
public class WeeklyScheduleDailyWorker {

  private final WeeklyScheduleKeeper weeklyScheduleKeeper;
  private final LocalDateTimeService localDateTimeService;
  private final IntervalScheduledTaskProcessor taskProcessor;
  private final ScheduledThreadPoolExecutor task;

  /**
   * Create new WeeklyScheduleDailyWorker.
   *
   * @param weeklyScheduleKeeper of all the tasks for for the week
   * @param localDateTimeService wrapping up {@code LocalDateTime}
   * @param taskProcessor allowing to schedule tasks
   */
  public WeeklyScheduleDailyWorker(
      WeeklyScheduleKeeper weeklyScheduleKeeper,
      LocalDateTimeService localDateTimeService,
      IntervalScheduledTaskProcessor taskProcessor) {
    this.weeklyScheduleKeeper = weeklyScheduleKeeper;
    this.localDateTimeService = localDateTimeService;
    this.taskProcessor = taskProcessor;
    this.task = new ScheduledThreadPoolExecutor(1);
  }

  /**
   * Starts scheduling all the tasks that can be run now and schedules revisits at midnight for each
   * day.
   *
   * <p>Tasks are started daily at midnight of the time the application is running in.
   */
  public void run() {
    task.schedule(getTask(), 0, TimeUnit.SECONDS);
    task.scheduleAtFixedRate(
        getTask(), getNanoTimeToMidnight(), TimeUnit.DAYS.toNanos(1L), TimeUnit.NANOSECONDS);
  }

  /**
   * The daily scheduling task.
   *
   * <p>It checks the day of the way of the day the task is executed on. It then retrieves tasks for
   * that day and delegates scheduling to {@link IntervalScheduledTaskProcessor}.
   *
   * @return the runnable task
   */
  private Runnable getTask() {
    return () -> {
      DayOfWeek dayOfWeekNow = localDateTimeService.dateNow().getDayOfWeek();
      List<IntervalScheduledTask> listOfTasks = weeklyScheduleKeeper.getTaskScheduled(dayOfWeekNow);
      listOfTasks.forEach(taskProcessor::schedule);
    };
  }

  /**
   * Provide time left from now till midnight in nanoseconds.
   *
   * @return time in nanoseconds
   */
  private long getNanoTimeToMidnight() {
    return LocalTime.MAX.toNanoOfDay() - localDateTimeService.timeNow().toNanoOfDay();
  }
}
