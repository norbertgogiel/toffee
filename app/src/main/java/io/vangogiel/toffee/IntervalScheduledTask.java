package io.vangogiel.toffee;

import java.util.concurrent.TimeUnit;

/**
 * Encapsulation class for data required to schedule a task.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see IntervalScheduledTaskAgent
 */
public class IntervalScheduledTask {

  private final Runnable runnable;
  private final IntervalScheduledTime delay;
  private final long period;
  private final TimeUnit timeUnit;

  /**
   * Create a new IntervalScheduledTask.
   *
   * @param runnable wrapping up a task
   * @param delay wrapping up the delay to start and shutdown
   * @param period period at which the task should run
   * @param timeUnit at which the period should occur
   */
  public IntervalScheduledTask(
      Runnable runnable, IntervalScheduledTime delay, long period, TimeUnit timeUnit) {
    this.runnable = runnable;
    this.delay = delay;
    this.period = period;
    this.timeUnit = timeUnit;
  }

  /**
   * Getter to retrieve the runnable.
   *
   * @return the runnable as {@link Runnable}
   */
  public Runnable getRunnable() {
    return runnable;
  }

  /**
   * Getter to retrieve the delay to start.
   *
   * @return the delay as {@code long}
   */
  public long getDelayToStart() {
    return delay.getDelayToStart();
  }

  /**
   * Getter to retrieve the period.
   *
   * @return the period as {@code long}
   */
  public long getPeriod() {
    return period;
  }

  /**
   * Getter to retrieve the delay to shutdown.
   *
   * @return the delay to shutdown as {@code long}
   */
  public long getDelayToShutdown() {
    return delay.getDelayToShutdown();
  }

  /**
   * Getter to retrieve the time unit.
   *
   * @return the time unit as {@code TimeUnit}
   */
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }
}
