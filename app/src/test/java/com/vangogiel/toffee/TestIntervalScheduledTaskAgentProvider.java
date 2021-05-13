package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

public class TestIntervalScheduledTaskAgentProvider {

  @Test
  public void testGet() {
    assertNotNull(new IntervalScheduledTaskAgentProvider().get());
  }
}
