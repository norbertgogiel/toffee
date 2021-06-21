package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Weekdays;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Keeper is responsible for holding a schedule for the week and registering all tasks for days in
 * the week.
 *
 * <p>The schedule is a {@code Map<>} of {@code DayOfWeek} with a {@code List<>} of tasks for each
 * day.
 *
 * <p>Tasks are planned as an entry in a {@code List<>} that is assigned to that day of the week.
 *
 * @author Norbert Gogiel
 * @since 1.0
 */
public class WeeklyScheduleKeeper {

  private final Map<DayOfWeek, List<IntervalScheduledTask>> schedule =
      new HashMap<>() {
        {
          put(DayOfWeek.MONDAY, new ArrayList<>());
          put(DayOfWeek.TUESDAY, new ArrayList<>());
          put(DayOfWeek.WEDNESDAY, new ArrayList<>());
          put(DayOfWeek.THURSDAY, new ArrayList<>());
          put(DayOfWeek.FRIDAY, new ArrayList<>());
          put(DayOfWeek.SATURDAY, new ArrayList<>());
          put(DayOfWeek.SUNDAY, new ArrayList<>());
        }
      };

  private final List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
  private final WeekdayAnnotationProcessor weekdayAnnotationProcessor;
  private final IntervalScheduledTaskProcessor taskProcessor;

  /**
   * Create a new WeeklyScheduleKeeper.
   *
   * @param weekdayAnnotationProcessor to convert raw annotation to meaningful object
   * @param taskProcessor to schedule tasks
   */
  public WeeklyScheduleKeeper(
      WeekdayAnnotationProcessor weekdayAnnotationProcessor,
      IntervalScheduledTaskProcessor taskProcessor) {
    this.weekdayAnnotationProcessor = weekdayAnnotationProcessor;
    this.taskProcessor = taskProcessor;
  }

  /**
   * Allows to create a wrapped task and schedule it appropriately.
   *
   * <p>The scheduling depends on the annotation provided.
   *
   * <p>If annotation is provided the task is scheduled as prescribed by the annotation. If the
   * annotation isn't provided them the tasks is scheduled for all the days in the week.
   *
   * @param source class to be able to create a task
   * @param method that is to be run from the class
   */
  public void planIn(Class<?> source, Method method) {
    IntervalScheduledTask task = taskProcessor.processRawAndWrap(source, method);
    if (method.isAnnotationPresent(Weekdays.class)) {
      Weekdays weekdays = method.getAnnotation(Weekdays.class);
      Set<DayOfWeek> scheduledDays = weekdayAnnotationProcessor.process(weekdays);
      scheduledDays.forEach(day -> schedule.get(day).add(task));
    } else {
      schedule.forEach((day, tasks) -> tasks.add(task));
    }
  }

  /**
   * Retrieve number of tasks scheduled for the day of the week.
   *
   * @param dayOfWeek for which the number of tasks is required
   * @return number of days as {@code int}
   */
  public int getNumberOfTasksScheduled(DayOfWeek dayOfWeek) {
    return schedule.get(dayOfWeek).size();
  }
}
