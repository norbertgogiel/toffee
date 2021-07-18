package io.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/** Test class for {@link IntervalScheduledTaskAgent}. */
public class TestIntervalScheduledTaskAgent {

  @Test
  public void testCreateInstanceOfIntervalScheduledTaskAgent() {
    assertDoesNotThrow(() -> new IntervalScheduledTaskAgent(1));
  }

  @Test
  public void testCorePoolSize() {
    IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
    assertEquals(2, subject.getCorePoolSize());
  }

  @Test
  public void testCurrentPoolSize() {
    IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
    assertEquals(0, subject.getCurrentPoolSize());
  }

  @Test
  public void testScheduleTask() throws InterruptedException {
    AtomicInteger atomicInteger = new AtomicInteger();
    IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
    IntervalScheduledTask task =
        new IntervalScheduledTask(
            atomicInteger::getAndIncrement,
            new IntervalScheduledTime(0, 1000),
            100,
            TimeUnit.MILLISECONDS);
    subject.submit(task);
    Thread.sleep(350);
    assertEquals(4, atomicInteger.get());
  }

  @Test
  public void testIntervalScheduleTask() throws InterruptedException {
    AtomicInteger atomicInteger = new AtomicInteger();
    IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(1);
    IntervalScheduledTask task =
        new IntervalScheduledTask(
            atomicInteger::getAndIncrement,
            new IntervalScheduledTime(0, 150),
            100,
            TimeUnit.MILLISECONDS);
    subject.submit(task);
    Thread.sleep(350);
    assertEquals(2, atomicInteger.get());
  }

  @Test
  public void testTaskAndCancel() throws InterruptedException {
    AtomicInteger atomicInteger = new AtomicInteger();
    IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(1);
    IntervalScheduledTask task =
        new IntervalScheduledTask(
            atomicInteger::getAndIncrement,
            new IntervalScheduledTime(0, 2000),
            400,
            TimeUnit.MILLISECONDS);
    subject.submit(task);
    Thread.sleep(410);
    subject.shutdownNow();
    assertEquals(2, atomicInteger.get());
    Thread.sleep(400);
    assertEquals(2, atomicInteger.get());
  }
}
