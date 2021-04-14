package com.norbertgogiel.toffee;

import java.lang.reflect.Method;

import com.norbertgogiel.toffee.annotations.Every;
import com.norbertgogiel.toffee.annotations.EveryHour;
import com.norbertgogiel.toffee.annotations.EveryMinute;
import com.norbertgogiel.toffee.annotations.EverySecond;

public class TimePeriodAnnotationProcessor {

    public long process(Method method) {
        if (method.isAnnotationPresent(EverySecond.class)) {
            return 1;
        } else if (method.isAnnotationPresent(EveryMinute.class)) {
            return 60;
        } else if (method.isAnnotationPresent(EveryHour.class)) {
            return 3600 ;
        } else if (method.isAnnotationPresent(Every.class)) {
            Every every = method.getDeclaredAnnotation(Every.class);
            int period = every.period();
            return every.timeUnit().toSeconds(period);
        }
        return 1;
    }
}
