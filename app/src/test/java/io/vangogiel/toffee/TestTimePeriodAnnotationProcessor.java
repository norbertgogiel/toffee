package io.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vangogiel.toffee.annotations.Every;
import io.vangogiel.toffee.annotations.EveryHour;
import io.vangogiel.toffee.annotations.EveryMinute;
import io.vangogiel.toffee.annotations.EverySecond;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/** Test class for {@link TimePeriodAnnotationProcessor}. */
public class TestTimePeriodAnnotationProcessor {

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testScheduledAtEverySecond() throws NoSuchMethodException {
    AnnotationProcessor<Method, Long> subject = new TimePeriodAnnotationProcessor();

    long result =
        subject.process(ScheduledMethodAnnotatedAtEverySecond.class.getMethod("testRunnable"));

    assertEquals(1, result);
  }

  @Test
  public void testIScheduledAtEveryMinute() throws NoSuchMethodException {
    AnnotationProcessor<Method, Long> subject = new TimePeriodAnnotationProcessor();

    long result =
        subject.process(ScheduledMethodAnnotatedAtEveryMinute.class.getMethod("testRunnable"));

    assertEquals(60, result);
  }

  @Test
  public void testScheduledAtVeryHour() throws NoSuchMethodException {
    AnnotationProcessor<Method, Long> subject = new TimePeriodAnnotationProcessor();

    long result =
        subject.process(ScheduledMethodAnnotatedAtEveryHour.class.getMethod("testRunnable"));

    assertEquals(3600, result);
  }

  @Test
  public void testScheduledAtVery2Seconds() throws NoSuchMethodException {
    AnnotationProcessor<Method, Long> subject = new TimePeriodAnnotationProcessor();

    long result =
        subject.process(ScheduledMethodAnnotatedAtEvery2Seconds.class.getMethod("testRunnable"));

    assertEquals(2, result);
  }

  @Test
  public void testNoAnnotation() throws NoSuchMethodException {
    AnnotationProcessor<Method, Long> subject = new TimePeriodAnnotationProcessor();

    long result = subject.process(NotAnnotated.class.getMethod("testRunnable"));

    assertEquals(1, result);
  }

  static class ScheduledMethodAnnotatedAtEverySecond {

    @EverySecond
    public void testRunnable() {}
  }

  static class ScheduledMethodAnnotatedAtEveryMinute {

    @EveryMinute
    public void testRunnable() {}
  }

  static class ScheduledMethodAnnotatedAtEveryHour {

    @EveryHour
    public void testRunnable() {}
  }

  static class ScheduledMethodAnnotatedAtEvery2Seconds {

    @Every(period = 2, timeUnit = TimeUnit.SECONDS)
    public void testRunnable() {}
  }

  static class NotAnnotated {

    public void testRunnable() {}
  }
}
