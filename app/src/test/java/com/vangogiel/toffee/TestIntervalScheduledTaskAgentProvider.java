package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

/** Test class for {@link IntervalScheduledTaskAgentProvider}. */
public class TestIntervalScheduledTaskAgentProvider {

  @Test
  public void testGet() {
    assertNotNull(new IntervalScheduledTaskAgentProvider().get());
  }
}
