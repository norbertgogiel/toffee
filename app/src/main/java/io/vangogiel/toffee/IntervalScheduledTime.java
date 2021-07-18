package io.vangogiel.toffee;

/**
 * Encapsulates delays for task startup and shutdown.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 */
public class IntervalScheduledTime {

  private final long delayToStart;
  private final long delayToShutdown;

  /**
   * Create a new IntervalScheduledTime.
   *
   * @param delayToStart of a task as {@code long}
   * @param delayToShutdown of a task as a {@code long}
   */
  public IntervalScheduledTime(long delayToStart, long delayToShutdown) {
    this.delayToStart = delayToStart;
    this.delayToShutdown = delayToShutdown;
  }

  /**
   * Getter to retrieve delay to start of a task.
   *
   * @return the delay as {@code long}
   */
  public long getDelayToStart() {
    return delayToStart;
  }

  /**
   * Getter to retrieve delay to shutdown of a task.
   *
   * @return the delay as {@code long}
   */
  public long getDelayToShutdown() {
    return delayToShutdown;
  }
}
