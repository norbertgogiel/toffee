package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Weekdays;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

  public WeeklyScheduleKeeper(
      WeekdayAnnotationProcessor weekdayAnnotationProcessor,
      IntervalScheduledTaskProcessor taskProcessor) {
    this.weekdayAnnotationProcessor = weekdayAnnotationProcessor;
    this.taskProcessor = taskProcessor;
  }

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

  public int getNumberOfTasksScheduled(DayOfWeek dayOfWeek) {
    return schedule.get(dayOfWeek).size();
  }
}
