package com.norbertgogiel.toffee;

import java.lang.reflect.Method;
import java.time.LocalTime;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

public class IntervalScheduledAnnotationProcessor {

    private final TimeParser timeParser;

    public IntervalScheduledAnnotationProcessor(TimeParser timeParser) {
        this.timeParser = timeParser;
    }

    public IntervalScheduledTime process(Method method) {
        ScheduledFrom rawFrom = method.getDeclaredAnnotation(ScheduledFrom.class);
        ScheduledUntil rawUntil = method.getDeclaredAnnotation(ScheduledUntil.class);
        LocalTime from = timeParser.validateAndParse(rawFrom.time());
        LocalTime until = timeParser.validateAndParse(rawUntil.time());
        long initDelay = calcRelativeToNow(from);
        long delayToShutdown = calcRelativeToNow(until);
        if (LocalTime.now().compareTo(from) > 0)
            initDelay = 86400 + initDelay;
        if (LocalTime.now().compareTo(until) < 0) {
            if (LocalTime.now().compareTo(from) > 0)
                initDelay = 0;
        } else {
            delayToShutdown = 86400 + delayToShutdown;
        }
        return new IntervalScheduledTime(initDelay, delayToShutdown);
    }

    private long calcRelativeToNow(LocalTime start) {
        return start.toSecondOfDay() - LocalTime.now().toSecondOfDay();
    }
}
