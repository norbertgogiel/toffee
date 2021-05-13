package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.EveryHour;
import com.vangogiel.toffee.annotations.EveryMinute;
import com.vangogiel.toffee.annotations.EverySecond;
import java.lang.reflect.Method;

public class TimePeriodAnnotationProcessor {

  public long process(Method method) {
    if (method.isAnnotationPresent(EverySecond.class)) {
      return 1;
    } else if (method.isAnnotationPresent(EveryMinute.class)) {
      return 60;
    } else if (method.isAnnotationPresent(EveryHour.class)) {
      return 3600;
    } else if (method.isAnnotationPresent(Every.class)) {
      Every every = method.getDeclaredAnnotation(Every.class);
      int period = every.period();
      return every.timeUnit().toSeconds(period);
    }
    return 1;
  }
}
