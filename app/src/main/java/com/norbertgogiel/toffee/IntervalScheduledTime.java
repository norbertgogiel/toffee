package com.norbertgogiel.toffee;

public class IntervalScheduledTime {

  private final long delayToStart;
  private final long delayToShutdown;

  public IntervalScheduledTime(long delayToStart, long delayToShutdown) {
    this.delayToStart = delayToStart;
    this.delayToShutdown = delayToShutdown;
  }

  public long getDelayToStart() {
    return delayToStart;
  }

  public long getDelayToShutdown() {
    return delayToShutdown;
  }
}
