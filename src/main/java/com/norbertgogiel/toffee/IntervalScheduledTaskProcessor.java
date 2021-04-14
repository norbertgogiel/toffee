package com.norbertgogiel.toffee;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

public class IntervalScheduledTaskProcessor {

    private List<IntervalScheduledTaskAgent> registeredAgents;
    private TimePeriodAnnotationProcessor timePeriodAnnotationProcessor;
    private IntervalScheduledAnnotationProcessor delayCalculator;
    private IntervalScheduledTaskAgentProvider agentProvider;

    public IntervalScheduledTaskProcessor(
            List<IntervalScheduledTaskAgent> registeredAgents,
            TimePeriodAnnotationProcessor timePeriodAnnotationProcessor,
            IntervalScheduledAnnotationProcessor delayCalculator,
            IntervalScheduledTaskAgentProvider agentProvider) {
        this.registeredAgents = registeredAgents;
        this.timePeriodAnnotationProcessor = timePeriodAnnotationProcessor;
        this.delayCalculator = delayCalculator;
        this.agentProvider = agentProvider;
    }

    public void tryScheduleTask(Class<?> source, Method method) {
        if (isMethodAValidSchedule(method))
            schedule(processRawAndWrap(source, method));
    }

    private IntervalScheduledTask processRawAndWrap(Class<?> source, Method method) {
        long period = timePeriodAnnotationProcessor.process(method);
        IntervalScheduledTime delay = delayCalculator.process(method);
        try {
            Runnable runnable = (Runnable) method.invoke(source.newInstance());
            return new IntervalScheduledTask(runnable, delay, period, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create a legal runnable", e);
        }
    }

    private void schedule(IntervalScheduledTask task) {
        IntervalScheduledTaskAgent agent = agentProvider.get();
        registeredAgents.add(agent);
        agent.submit(task);
    }

    private boolean isMethodAValidSchedule(Method method) {
        return method.isAnnotationPresent(ScheduledFrom.class)
                && method.isAnnotationPresent(ScheduledUntil.class)
                && method.getReturnType() == Runnable.class;
    }
}
