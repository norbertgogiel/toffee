package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToffeeApplication {

    private List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
    private IntervalScheduledTaskProcessor intervalScheduledTaskProcessor;

    public ToffeeApplication() {
        TimeParser timeParser = new TimeParser();
        TimePeriodAnnotationProcessor timePeriodAnnotationProcessor = new TimePeriodAnnotationProcessor();
        intervalScheduledTaskProcessor = new IntervalScheduledTaskProcessor(
                registeredAgents,
                timeParser,
                timePeriodAnnotationProcessor
        );
    }

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

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    private void processSource(Class<?> source) {
        Arrays.stream(source.getMethods())
                .forEach(method -> {
                    if (isMethodAValidSchedule(method))
                        intervalScheduledTaskProcessor.scheduleTask(source, method);
                });
    }

    private boolean isMethodAValidSchedule(Method method) {
        return method.isAnnotationPresent(ScheduledFrom.class)
                && method.isAnnotationPresent(ScheduledUntil.class)
                && method.getReturnType() == Runnable.class;
    }
}
