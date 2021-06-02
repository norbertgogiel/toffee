package com.vangogiel.toffee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalTime;
import org.junit.Test;

/** Test class for {@link LocalTimeService}. */
public class TestLocalTimeService {

  @Test
  public void testNow() {
    LocalTimeService localTimeService = new LocalTimeService();
    assertNotNull(localTimeService.now());
    assertEquals(LocalTime.now().getHour(), localTimeService.now().getHour());
    assertEquals(LocalTime.now().getMinute(), localTimeService.now().getMinute());
    assertEquals(LocalTime.now().getSecond(), localTimeService.now().getSecond());
  }
}
