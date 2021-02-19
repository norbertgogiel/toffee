package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ToffeeApplication {

    public void init(Class<?> source) {
        assertNotNull(source);
        if (source.isAnnotationPresent(IntervalScheduled.class)) {
            Arrays.stream(source.getMethods())
                    .forEach(method -> {
                        if (method.isAnnotationPresent(ScheduledFrom.class)
                        && method.isAnnotationPresent(ScheduledUntil.class)
                        && method.getReturnType() == Runnable.class)
                                scheduleTask(source, method);
            });
        }
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    private void scheduleTask(Class<?> source, Method method) {
        IntervalScheduledTaskAgent agent = new IntervalScheduledTaskAgent(1);
        ScheduledFrom scheduledFrom = method.getDeclaredAnnotation(ScheduledFrom.class);
        ScheduledUntil scheduledUntil = method.getDeclaredAnnotation(ScheduledUntil.class);
        LocalTime scheduledFromLocalTime = LocalTime.of(
                scheduledFrom.hour(),
                scheduledFrom.minute(),
                scheduledFrom.second(),
                scheduledFrom.nano());
        LocalTime scheduledUntilLocalTime = LocalTime.of(
                scheduledUntil.hour(),
                scheduledUntil.minute(),
                scheduledUntil.second(),
                scheduledUntil.nano());
        try {
            Runnable runnable = (Runnable) method.invoke(source.newInstance());
            agent.submit(runnable,
                    Duration.between(LocalTime.now(), scheduledFromLocalTime).toMillis(),
                    1,
                    Duration.between(LocalTime.now(), scheduledUntilLocalTime).toMillis(),
                    TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create a legal runnable", e);
        }
    }
}
