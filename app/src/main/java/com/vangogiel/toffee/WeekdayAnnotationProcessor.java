package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class WeekdayAnnotationProcessor {

  public Set<DayOfWeek> process(Weekdays annotation) {
    Set<DayOfWeek> weekdays = new HashSet<>();
    String rawWeekdays = annotation.days();
    if (rawWeekdays.contains("Mon")) {
      weekdays.add(DayOfWeek.MONDAY);
    }
    if (rawWeekdays.contains("Tue")) {
      weekdays.add(DayOfWeek.TUESDAY);
    }
    if (rawWeekdays.contains("Wed")) {
      weekdays.add(DayOfWeek.WEDNESDAY);
    }
    if (rawWeekdays.contains("Thu")) {
      weekdays.add(DayOfWeek.THURSDAY);
    }
    if (rawWeekdays.contains("Fri")) {
      weekdays.add(DayOfWeek.FRIDAY);
    }
    if (rawWeekdays.contains("Sat")) {
      weekdays.add(DayOfWeek.SATURDAY);
    }
    if (rawWeekdays.contains("Sun")) {
      weekdays.add(DayOfWeek.SUNDAY);
    }
    return weekdays;
  }
}
