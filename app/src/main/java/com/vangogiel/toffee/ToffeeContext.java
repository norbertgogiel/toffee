package com.vangogiel.toffee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This is a main application entry-point initialising application context at constructor level.
 *
 * <p>It instantiates the dependencies required to run the application and passes them to key
 * processor, {@link IntervalScheduledTaskProcessor}.
 *
 * <p>The class also exposes number of methods to allow to query the context for its number of tasks
 * in process or to shut all of them down.
 *
 * @author Norbert Gogiel
 * @since 1.0
 */
public class ToffeeContext {

  private final List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
  private final IntervalScheduledTaskProcessor intervalScheduledTaskProcessor;

  /**
   * Create a new ToffeeContext.
   *
   * @param sources as vararg classes containing annotated scheduled methods
   */
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

  /**
   * Getter to get total core pool size of all registered agents.
   *
   * @return value as {@code int}
   */
  public int getTotalCorePoolSize() {
    return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum();
  }

  /**
   * Getter to get total current pool size of all registered agents.
   *
   * @return value as {@code int}
   */
  public int getTotalCurrentPoolSize() {
    return registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum();
  }

  /**
   * Iterates through all tasks, shuts each single one down and unregisters it.
   *
   * <p>It halts the agent responsible for running the task and the agent responsible for shutting
   * down the task.
   */
  public void shutDownAllTasksNow() {
    for (Iterator<IntervalScheduledTaskAgent> agents = registeredAgents.iterator();
        agents.hasNext(); ) {
      IntervalScheduledTaskAgent agent = agents.next();
      agent.shutdownNow();
      agents.remove();
    }
  }

  /**
   * Consumes classes as varargs and delegates each one in a stream to processing method.
   *
   * @param sources as an array of classes to process
   */
  private void processSources(Class<?>... sources) {
    Arrays.stream(sources).forEach(this::processSource);
  }

  /**
   * Processes each class by delegating the task of scheduling to {@link
   * IntervalScheduledTaskProcessor}.
   *
   * <p>It asserts whether the class is a null or not before processing. It then streams through all
   * the methods in the class and submits each one of them to schedule without assessing its
   * suitability for scheduling.
   *
   * @param source class to be processed for scheduling
   * @throws IllegalArgumentException if the supplied class is null.
   */
  private void processSource(Class<?> source) {
    assertNotNull(source);
    Arrays.stream(source.getMethods())
        .forEach(method -> intervalScheduledTaskProcessor.tryScheduleTask(source, method));
  }

  /**
   * Asserts a object is not null.
   *
   * <p>If the object is null then {@link IllegalArgumentException} is thrown.
   *
   * @param object to be asserted as not null
   * @throws IllegalArgumentException if the object provided is null
   */
  private static void assertNotNull(Object object) {
    if (object == null) throw new IllegalArgumentException("Object must not be null");
  }
}
