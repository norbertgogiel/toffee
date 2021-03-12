package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.Every;
import com.norbertgogiel.toffee.annotations.EveryHour;
import com.norbertgogiel.toffee.annotations.EveryMinute;
import com.norbertgogiel.toffee.annotations.EverySecond;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IntervalScheduledTaskProcessor {

    private List<IntervalScheduledTaskAgent> registeredAgents;

    public IntervalScheduledTaskProcessor(List<IntervalScheduledTaskAgent> registeredAgents) {
        this.registeredAgents = registeredAgents;
    }

    public void scheduleTask(Class<?> source, Method method) {
        IntervalScheduledTask task = extractAndPrepareTask(source, method);
        tryToSchedule(task);
    }

    private IntervalScheduledTask extractAndPrepareTask(Class<?> source, Method method) {
        ScheduledFrom scheduledFrom = method.getDeclaredAnnotation(ScheduledFrom.class);
        ScheduledUntil scheduledUntil = method.getDeclaredAnnotation(ScheduledUntil.class);
        LocalTime scheduledFromLocalTime = validateAndParse(scheduledFrom.time());
        LocalTime scheduledUntilLocalTime = validateAndParse(scheduledUntil.time());
        long period = tryGetPeriodFromAnnotation(method);
        long initDelay;
        long delayToShutdown;
        if (LocalTime.now().compareTo(scheduledFromLocalTime) < 0) {
            initDelay = scheduledFromLocalTime.toSecondOfDay() - LocalTime.now().toSecondOfDay() ;
        } else {
            initDelay = (24 * 60 * 60) - (LocalTime.now().toSecondOfDay() - scheduledFromLocalTime.toNanoOfDay());
        }
        if (LocalTime.now().compareTo(scheduledUntilLocalTime) < 0) {
            delayToShutdown = scheduledUntilLocalTime.toSecondOfDay() - LocalTime.now().toSecondOfDay();
            if (LocalTime.now().compareTo(scheduledFromLocalTime) > 0) {
                initDelay = 0;
            }
        } else {
            delayToShutdown = (24 * 60 * 60) - LocalTime.now().toSecondOfDay() - scheduledUntilLocalTime.toSecondOfDay();
        }
        try {
            Runnable runnable = (Runnable) method.invoke(source.newInstance());
            return new IntervalScheduledTask(runnable, initDelay, period, delayToShutdown, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create a legal runnable", e);
        }
    }

    private LocalTime validateAndParse(String time) {
        String[] timeArr = time.split(":");
        if (timeArr.length != 3) {
            throw new IllegalFormatPrecisionException(timeArr.length);
        }
        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);
        int second = Integer.parseInt(timeArr[2]);
        if (hour < 0 || hour > 23) {
            throw new IllegalFormatPrecisionException(hour);
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalFormatPrecisionException(minute);
        }
        if (second < 0 || second > 59) {
            throw new IllegalFormatPrecisionException(second);
        }
        return LocalTime.of(hour, minute, second);
    }

    private void tryToSchedule(IntervalScheduledTask task) {
        IntervalScheduledTaskAgent agent = new IntervalScheduledTaskAgent(1);
        registeredAgents.add(agent);
        agent.submit(task);
    }

    private long tryGetPeriodFromAnnotation(Method method) {
        if (method.isAnnotationPresent(EverySecond.class)) {
            return 1;
        } else if (method.isAnnotationPresent(EveryMinute.class)) {
            return 60;
        } else if (method.isAnnotationPresent(EveryHour.class)) {
            return 60 * 60 ;
        } else if (method.isAnnotationPresent(Every.class)) {
            Every every = method.getDeclaredAnnotation(Every.class);
            int period = every.period();
            return every.timeUnit().toSeconds(period);
        }
        return 1;
    }
}
