package io.vangogiel.toffee;

import io.vangogiel.toffee.annotations.Weekdays;
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

  private final WeekdayAnnotationProcessor weekdayAnnotationProcessor;

  /**
   * Create a new WeeklyScheduleKeeper.
   *
   * @param weekdayAnnotationProcessor to convert raw annotation to meaningful object
   */
  public WeeklyScheduleKeeper(WeekdayAnnotationProcessor weekdayAnnotationProcessor) {
    this.weekdayAnnotationProcessor = weekdayAnnotationProcessor;
  }

  /**
   * Allows to schedule a task according to annotation styling with the provided method.
   *
   * <p>If annotation is provided the task is scheduled as prescribed by the annotation. If the
   * annotation isn't provided them the tasks is scheduled for all the days in the week.
   *
   * @param task to be scheduled
   * @param method that is to be runs from the class
   */
  public void planIn(IntervalScheduledTask task, Method method) {
    if (method.isAnnotationPresent(Weekdays.class)) {
      scheduleForTheDay(method, task);
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

  /**
   * Retrieve tasks scheduled for the day.
   *
   * @param dayOfWeek for which scheduled tasks are to be retrieved
   * @return the list of tasks for the day requested
   */
  public List<IntervalScheduledTask> getTaskScheduled(DayOfWeek dayOfWeek) {
    return schedule.getOrDefault(dayOfWeek, new ArrayList<>());
  }

  /**
   * This method schedules a task for the day the method is scheduled with by {@link
   * io.vangogiel.toffee.annotations.Weekdays}.
   *
   * @param method annotated with {@code Weekdays}
   * @param task to be scheduled
   */
  private void scheduleForTheDay(Method method, IntervalScheduledTask task) {
    Weekdays weekdays = method.getAnnotation(Weekdays.class);
    Set<DayOfWeek> scheduledDays = weekdayAnnotationProcessor.process(weekdays);
    scheduledDays.forEach(day -> schedule.get(day).add(task));
  }
}
