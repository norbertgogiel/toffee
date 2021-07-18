package io.vangogiel.toffee;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/** Test class for {@link ScheduledTaskRunnable}. */
public class TestScheduledTaskRunnable {

  private static final AtomicInteger atomicInteger = new AtomicInteger();

  @Test
  public void testInvokeThrowsIllegalAccessException() throws NoSuchMethodException {
    ScheduledTaskRunnable runnable =
        new ScheduledTaskRunnable(
            TestIllegalAccessExcpetion.class,
            TestIllegalAccessExcpetion.class.getMethod("testMethod"));
    assertThrows(UndeclaredThrowableException.class, runnable::run);
  }

  @Test
  public void testInvokeThrowsIllegalArgumentException() throws NoSuchMethodException {
    ScheduledTaskRunnable runnable =
        new ScheduledTaskRunnable(
            TestIllegalArgumentException.class,
            TestIllegalArgumentException.class.getMethod("testMethod"));
    assertThrows(UndeclaredThrowableException.class, runnable::run);
  }

  @Test
  public void testInvokeThrowsInvocationTargetException() throws NoSuchMethodException {
    ScheduledTaskRunnable runnable =
        new ScheduledTaskRunnable(
            TestInstantiationException.class,
            TestInstantiationException.class.getMethod("testMethod"));
    assertThrows(UndeclaredThrowableException.class, runnable::run);
  }

  @Test
  public void testInvokeThrowsNoSuchMethodException() throws NoSuchMethodException {
    ScheduledTaskRunnable runnable =
        new ScheduledTaskRunnable(
            TestNoSuchMethodException.class,
            TestNoSuchMethodException.class.getMethod("testMethod"));
    assertThrows(UndeclaredThrowableException.class, runnable::run);
  }

  @Test
  public void testRunnable() throws NoSuchMethodException {
    ScheduledTaskRunnable runnable =
        new ScheduledTaskRunnable(TestClass.class, TestClass.class.getMethod("testMethod"));
    assertEquals(0, atomicInteger.get());
    runnable.run();
    assertEquals(1, atomicInteger.get());
  }

  static class TestClass {

    public void testMethod() {
      atomicInteger.getAndIncrement();
    }
  }

  static class TestIllegalAccessExcpetion {

    public void testMethod() throws IllegalAccessException {
      throw new IllegalAccessException();
    }
  }

  static class TestIllegalArgumentException {

    public void testMethod() {
      throw new IllegalArgumentException();
    }
  }

  static class TestInstantiationException {

    public void testMethod() throws InstantiationException {
      throw new InstantiationException();
    }
  }

  static class TestNoSuchMethodException {

    public void testMethod() throws NoSuchMethodException {
      throw new NoSuchMethodException();
    }
  }
}
