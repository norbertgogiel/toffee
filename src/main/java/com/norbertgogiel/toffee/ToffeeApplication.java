package com.norbertgogiel.toffee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToffeeApplication {

    private List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
    private IntervalScheduledTaskProcessor intervalScheduledTaskProcessor;

    public ToffeeApplication() {
        TimeParser timeParser = new TimeParser();
        TimePeriodAnnotationProcessor timePeriodAnnotationProcessor = new TimePeriodAnnotationProcessor();
        IntervalScheduledAnnotationProcessor delayCalculator = new IntervalScheduledAnnotationProcessor(timeParser);
        IntervalScheduledTaskAgentProvider agentProvider = new IntervalScheduledTaskAgentProvider();
        intervalScheduledTaskProcessor = new IntervalScheduledTaskProcessor(
                registeredAgents,
                timePeriodAnnotationProcessor,
                delayCalculator,
                agentProvider
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
                .forEach(method -> intervalScheduledTaskProcessor.tryScheduleTask(source, method));
    }
}
