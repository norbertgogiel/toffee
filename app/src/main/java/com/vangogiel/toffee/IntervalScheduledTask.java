package com.vangogiel.toffee;

import java.util.concurrent.TimeUnit;

public class IntervalScheduledTask {

  private final Runnable runnable;
  private final IntervalScheduledTime delay;
  private final long period;
  private final TimeUnit timeUnit;

  public IntervalScheduledTask(
      Runnable runnable, IntervalScheduledTime delay, long period, TimeUnit timeUnit) {
    this.runnable = runnable;
    this.delay = delay;
    this.period = period;
    this.timeUnit = timeUnit;
  }

  public Runnable getRunnable() {
    return runnable;
  }

  public long getDelayToStart() {
    return delay.getDelayToStart();
  }

  public long getPeriod() {
    return period;
  }

  public long getDelayToShutdown() {
    return delay.getDelayToShutdown();
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }
}
