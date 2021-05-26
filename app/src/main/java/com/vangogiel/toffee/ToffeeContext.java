package com.vangogiel.toffee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ToffeeContext {

  private List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
  private IntervalScheduledTaskProcessor intervalScheduledTaskProcessor;

  public ToffeeContext(Class<?>... sources) {
    TimeParser timeParser = new TimeParser();
    TimePeriodAnnotationProcessor timePeriodAnnotationProcessor =
        new TimePeriodAnnotationProcessor();
    IntervalScheduledAnnotationProcessor delayCalculator =
        new IntervalScheduledAnnotationProcessor(timeParser);
    IntervalScheduledTaskAgentProvider agentProvider = new IntervalScheduledTaskAgentProvider();
    intervalScheduledTaskProcessor =
        new IntervalScheduledTaskProcessor(
            registeredAgents, timePeriodAnnotationProcessor, delayCalculator, agentProvider);
    processSources(sources);
  }

  public int getTotalCorePoolSize() {
    return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum();
  }

  public int getTotalCurrentPoolSize() {
    return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum();
  }

  public void shutDownAllTasksNow() {
    for (Iterator<IntervalScheduledTaskAgent> agents = registeredAgents.iterator();
        agents.hasNext(); ) {
      IntervalScheduledTaskAgent agent = agents.next();
      agent.shutdownNow();
      agents.remove();
    }
  }

  private void processSources(Class<?>... sources) {
    Arrays.stream(sources).forEach(this::processSource);
  }

  private void processSource(Class<?> source) {
    assertNotNull(source);
    Arrays.stream(source.getMethods())
        .forEach(method -> intervalScheduledTaskProcessor.tryScheduleTask(source, method));
  }

  private static void assertNotNull(Object object) {
    if (object == null) throw new IllegalArgumentException("Object must not be null");
  }
}
