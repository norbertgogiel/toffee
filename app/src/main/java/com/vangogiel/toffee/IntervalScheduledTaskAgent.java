package com.vangogiel.toffee;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Class representing and encapsulating a scheduled task.
 *
 * <p>Upon instantiation it creates two {@link ScheduledThreadPoolExecutor}. One to schedule and run
 * a task and another one to schedule a shutdown of a task.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see ToffeeContext
 * @see ScheduledThreadPoolExecutor
 */
class IntervalScheduledTaskAgent {

  private final ScheduledThreadPoolExecutor taskAgent;
  private final ScheduledThreadPoolExecutor shutdownAgent;

  /**
   * Create a new IntervalScheduledTaskAgent.
   *
   * @param corePoolSize the core pool size for both Executors
   */
  public IntervalScheduledTaskAgent(int corePoolSize) {
    taskAgent = new ScheduledThreadPoolExecutor(corePoolSize);
    shutdownAgent = new ScheduledThreadPoolExecutor(corePoolSize);
  }

  /**
   * Getter to get core pool size.
   *
   * <p>Only core pool size of one agent is required to be returned. The running of the task and the
   * scheduled shutdown are considered to be a single process for the caller requesting this
   * information.
   *
   * @return core pool size as {@code int}
   */
  public int getCorePoolSize() {
    return taskAgent.getCorePoolSize();
  }

  /**
   * Get the current number of threads in the pool.
   *
   * <p>Only core pool size of one agent is required to be returned. The running of the task and the
   * scheduled shutdown are considered to be a single process for the caller requesting this
   * information.
   *
   * @return current pool size as {@code int}
   */
  public int getCurrentPoolSize() {
    return taskAgent.getPoolSize();
  }

  /**
   * Attempts to stop all actively executing tasks and halts the processing of waiting tasks.
   *
   * <p>It halts the task agent responsible for running the task and the agent responsible for
   * shutting the task down.
   *
   * <p>There are no guarantees beyond best-effort attempts to stop processing actively executing
   * tasks. This implementation interrupts tasks via Thread.interrupt; any task that fails to
   * respond to interrupts may never terminate.
   */
  public void shutdownNow() {
    taskAgent.shutdownNow();
    shutdownAgent.shutdownNow();
  }

  /**
   * Submit a {@link Runnable} task to be scheduled periodically at a fixed rate with an initial
   * delay and a shutdown hook scheduled to close the task. The shutdown delay is what schedules the
   * shutdown hook. The task executions continues until the shutdown task engages after a pre-set
   * delay.
   *
   * <p>If the shutdown delay is smaller than the delay to start the task, the task will never
   * terminate and will run indefinitely.
   *
   * <p>The cancellation attempt will fail if the task has already completed, has already been
   * cancelled, or could not be cancelled for some other reason.
   *
   * @param task encapsulating all the information required to schedule a task.
   * @throws java.util.concurrent.RejectedExecutionException if the task cannot be scheduled for
   *     execution
   * @throws NullPointerException if the command or unit is null
   * @throws IllegalArgumentException if period is less than or equal to zero
   */
  public void submit(IntervalScheduledTask task) {
    Future<?> future =
        taskAgent.scheduleAtFixedRate(
            task.getRunnable(), task.getDelayToStart(), task.getPeriod(), task.getTimeUnit());
    shutdownAgent.schedule(
        () -> future.cancel(false), task.getDelayToShutdown(), task.getTimeUnit());
  }
}
