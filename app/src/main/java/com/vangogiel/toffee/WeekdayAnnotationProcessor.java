package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Weekdays;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Processing class responsible for converting weekdays from an annotation into a set of days of
 * week.
 *
 * <p>An annotation, {@link com.vangogiel.toffee.annotations.Weekdays} will be passed into the
 * method, which will then try to parse the contents of it into a {@link Set} of {@link
 * java.time.DayOfWeek}.
 *
 * <p>Each day of week in an annotation is to be specified as per {@link #daysOfWeek}. If not
 * specified as that the processor will ignore that day.
 *
 * <p>Days can be listed in a single {@code String} individually to specify a single day of the week
 * or in groups separated by comma.
 *
 * <p>Range can also be specified, such as "Mon-Fri" to include all 5 days.
 *
 * <p>The two can be mixed in order to list a mixed set of days in a concise way, such as
 * "Mon-Thu,Sat" in order to include 5 days of the week.
 *
 * @author Norbert Gogiel
 * @since 1.0
 */
public class WeekdayAnnotationProcessor implements AnnotationProcessor<Weekdays, Set<DayOfWeek>> {

  List<String> daysOfWeek = List.of("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

  public Set<DayOfWeek> process(Weekdays annotation) {
    Set<DayOfWeek> weekdays = new HashSet<>();
    String rawWeekdays = annotation.days();
    Arrays.stream(rawWeekdays.split(","))
        .forEach(
            day -> {
              if (day.length() == 3) {
                weekdays.add(DayOfWeek.of(daysOfWeek.indexOf(day) + 1));
              } else if (day.contains("-") && day.length() == 7) {
                String[] daysRange = day.split("-");
                int start = daysOfWeek.indexOf(daysRange[0]);
                int end = daysOfWeek.indexOf(daysRange[1]);
                for (int i = start; i >= 0 && i <= end; i++) {
                  weekdays.add(DayOfWeek.of(i + 1));
                }
              }
            });
    return weekdays;
  }
}
