package com.vangogiel.toffee;

public class IntervalScheduledTaskAgentProvider {

  public IntervalScheduledTaskAgent get() {
    return new IntervalScheduledTaskAgent(1);
  }
}
