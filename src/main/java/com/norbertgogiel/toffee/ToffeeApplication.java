package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ToffeeApplication {

    private List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();

    public void init(Class<?> source) {
        assertNotNull(source);
        processSource(source);
    }

    public int getTotalCorePoolSize() {
        return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum();
    }

    public int getTotalCurrentPoolSize() {
        return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum();
    }

    public int getTotalCurrentTaskCount() {
        return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentTaskCount).sum();
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    private void processSource(Class<?> source) {
        if (source.isAnnotationPresent(IntervalScheduled.class)) {
            Arrays.stream(source.getMethods())
                    .forEach(method -> {
                        if (isMethodAValidSchedule(method))
                            scheduleTask(source, method);
                    });
        }
    }

    private boolean isMethodAValidSchedule(Method method) {
        return method.isAnnotationPresent(ScheduledFrom.class)
                && method.isAnnotationPresent(ScheduledUntil.class)
                && method.getReturnType() == Runnable.class;
    }

    private void scheduleTask(Class<?> source, Method method) {
        ScheduledFrom scheduledFrom = method.getDeclaredAnnotation(ScheduledFrom.class);
        ScheduledUntil scheduledUntil = method.getDeclaredAnnotation(ScheduledUntil.class);
        LocalTime scheduledFromLocalTime = validateAndParse(scheduledFrom.time());
        LocalTime scheduledUntilLocalTime = validateAndParse(scheduledUntil.time());
        long initDelay = LocalTime.now().toSecondOfDay() - scheduledFromLocalTime.toSecondOfDay();
        long delayToShutdown = LocalTime.now().toSecondOfDay() - scheduledUntilLocalTime.toSecondOfDay();
        if (LocalTime.now().compareTo(scheduledFromLocalTime) > 0) {
            initDelay = (24 * 60 * 60) - initDelay;
            delayToShutdown = (24 * 60 * 60) - delayToShutdown;
        }
        tryToSchedule(source, method, initDelay, delayToShutdown);
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

    private void tryToSchedule(Class<?> source, Method method, long initDelay, long delayToShutdown) {
        IntervalScheduledTaskAgent agent = new IntervalScheduledTaskAgent(1);
        registeredAgents.add(agent);
        try {
            Runnable runnable = (Runnable) method.invoke(source.newInstance());
            agent.submit(runnable, initDelay, 1, delayToShutdown, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create a legal runnable", e);
        }
    }
}
