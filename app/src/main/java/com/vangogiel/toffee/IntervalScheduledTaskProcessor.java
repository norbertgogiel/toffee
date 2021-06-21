package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.lang.reflect.Method;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Processes methods annotated with {@code ScheduledFrom} and {@code ScheduledUntil} to be invoked
 * by {@code ToffeeContext}.
 *
 * <p>If a method is correctly annotated then that method is passed to scheduling based on the
 * information provided.
 *
 * <p>Data in annotations are processed and wrapped along with the method and delegated to another
 * method to be scheduled.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see ToffeeContext
 */
public class IntervalScheduledTaskProcessor {

  private final List<IntervalScheduledTaskAgent> registeredAgents;
  private final AnnotationProcessor<Method, Long> timePeriodAnnotationProcessor;
  private final AnnotationProcessor<Method, IntervalScheduledTime> delayCalculator;
  private final IntervalScheduledTaskAgentProvider agentProvider;

  /**
   * Create a new IntervalScheduledTaskProcessor.
   *
   * @param registeredAgents an empty {@link List} supplied to store all registered agents
   * @param timePeriodAnnotationProcessor to process annotations that set period for the task run
   * @param delayCalculator to calculate delays to startup and shutdown wrapped in annotations
   * @param agentProvider to provide scheduling agent instance
   */
  public IntervalScheduledTaskProcessor(
      List<IntervalScheduledTaskAgent> registeredAgents,
      AnnotationProcessor<Method, Long> timePeriodAnnotationProcessor,
      AnnotationProcessor<Method, IntervalScheduledTime> delayCalculator,
      IntervalScheduledTaskAgentProvider agentProvider) {
    this.registeredAgents = registeredAgents;
    this.timePeriodAnnotationProcessor = timePeriodAnnotationProcessor;
    this.delayCalculator = delayCalculator;
    this.agentProvider = agentProvider;
  }

  /**
   * Tries to schedule a task, provided it is a valid method to be scheduled.
   *
   * <p>It delegates raw method to be wrapped in an object and delegated to another method to be
   * scheduled. If a method is not appropriately annotated, as per check, the method is ignored and
   * not processed.
   *
   * @param source the source class of the method
   * @param method the method in subject
   */
  public void tryScheduleTask(Class<?> source, Method method) {
    if (isMethodAValidSchedule(method)) schedule(processRawAndWrap(source, method));
  }

  /**
   * Consumes raw method and delegates annotation processing to other dependencies.
   *
   * <p>The method is wrapped in a runnable class {@code ScheduledTaskRunnable} and all together
   * wrapped in a {@code IntervalScheduledTask} and returned to the caller.
   *
   * @param source the source class of the method
   * @param method the method in subject
   * @return all the information for a schedule task wrapped in {@code IntervalScheduledTask}
   * @throws IllegalFormatPrecisionException if the time in the annotation is of incorrect format
   * @throws java.time.DateTimeException if the time is out of its relative time bounds
   */
  public IntervalScheduledTask processRawAndWrap(Class<?> source, Method method) {
    long period = timePeriodAnnotationProcessor.process(method);
    IntervalScheduledTime delay = delayCalculator.process(method);
    Runnable runnable = new ScheduledTaskRunnable(source, method);
    return new IntervalScheduledTask(runnable, delay, period, TimeUnit.SECONDS);
  }

  /**
   * Consumes a task and adds it to registeredAgents list and submits it to the {@link
   * IntervalScheduledTaskAgent} to be scheduled.
   *
   * @param task to be scheduled
   * @throws NullPointerException if the command in the task or unit is null
   * @throws IllegalArgumentException if scheduled period is less than or equal to zero
   */
  private void schedule(IntervalScheduledTask task) {
    IntervalScheduledTaskAgent agent = agentProvider.get();
    registeredAgents.add(agent);
    agent.submit(task);
  }

  /**
   * Verifies a method is annotated with {@link ScheduledUntil} and {@link ScheduledFrom}.
   *
   * @param method method to be verified
   * @return a {@code boolean} indicating whether a method is annotated correctly
   */
  private boolean isMethodAValidSchedule(Method method) {
    return method.isAnnotationPresent(ScheduledFrom.class)
        && method.isAnnotationPresent(ScheduledUntil.class);
  }
}
