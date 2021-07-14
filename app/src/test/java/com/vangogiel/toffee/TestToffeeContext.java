package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.io.IOException;
import java.time.Clock;
import org.junit.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

/** Test class for {@link ToffeeContext} */
public class TestToffeeContext {

  @Test
  public void testDefaultConstructor() {
    assertDoesNotThrow((ThrowingSupplier<ToffeeContext>) ToffeeContext::new);
  }

  @Test
  public void testConstructorWithClock() {
    assertDoesNotThrow(() -> new ToffeeContext(Clock.systemDefaultZone()));
  }

  @Test
  public void testIncludeObject() {
    assertDoesNotThrow(() -> new ToffeeContext(TestToffeeContext.class));
  }

  @Test
  public void testIncludeNullObjectAndThrow() {
    assertThrows(IllegalArgumentException.class, () -> new ToffeeContext((Class<?>) null));
  }

  @Test
  public void testGetTotalCorePoolSize() {
    ToffeeContext subject = new ToffeeContext();
    assertEquals(0, subject.getTotalCorePoolSize());
  }

  @Test
  public void testGetTotalCurrentPoolSize() {
    ToffeeContext subject = new ToffeeContext();
    assertEquals(0, subject.getTotalCurrentPoolSize());
  }

  @Test
  public void testIncludeIntervalScheduledNonNullClassWithMethod() {
    assertDoesNotThrow(() -> new ToffeeContext(IntervalScheduledExceptionClass.class));
  }

  @Test
  public void testInitWithMoreThanOneClass() {
    assertDoesNotThrow(
        () ->
            new ToffeeContext(
                IntervalScheduledTestClass.class,
                AlternativeIntervalScheduledExceptionClass.class));
  }

  @Test
  public void testShutDownAllTasksNow() throws InterruptedException {
    ToffeeContext subject = new ToffeeContext(IntervalScheduledTestClass.class);
    Thread.sleep(2000);
    assertEquals(1, subject.getTotalCorePoolSize());
    subject.shutDownAllTasksNow();
    assertEquals(0, subject.getTotalCorePoolSize());
  }

  static class IntervalScheduledExceptionClass {

    public Runnable testRunnable() throws IOException {
      throw new IOException("evil");
    }
  }

  static class AlternativeIntervalScheduledExceptionClass {

    public Runnable testRunnable() throws IOException {
      throw new IOException("evil");
    }
  }

  static class IntervalScheduledTestClass {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "00:00:01")
    public void testRunnable() throws IOException {}
  }
}
