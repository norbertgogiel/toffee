package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    if (isMethodAValidSchedule(method)) schedule(processRawAndWrap(source, method));
  }

  private IntervalScheduledTask processRawAndWrap(Class<?> source, Method method) {
    long period = timePeriodAnnotationProcessor.process(method);
    IntervalScheduledTime delay = delayCalculator.process(method);
    Runnable runnable = new ScheduledTaskRunnable(source, method);
    return new IntervalScheduledTask(runnable, delay, period, TimeUnit.SECONDS);
  }

  private void schedule(IntervalScheduledTask task) {
    IntervalScheduledTaskAgent agent = agentProvider.get();
    registeredAgents.add(agent);
    agent.submit(task);
  }

  private boolean isMethodAValidSchedule(Method method) {
    return method.isAnnotationPresent(ScheduledFrom.class)
        && method.isAnnotationPresent(ScheduledUntil.class);
  }
}
