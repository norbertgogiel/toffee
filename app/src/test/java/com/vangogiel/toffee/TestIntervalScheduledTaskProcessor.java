package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** Test class for {@link IntervalScheduledTaskProcessor}. */
public class TestIntervalScheduledTaskProcessor {

  @Mock private TimePeriodAnnotationProcessor mockTimePeriodAnnotationProcessor;
  @Mock private AnnotationProcessor<Method, IntervalScheduledTime> mockDelayCalculator;
  @Mock private IntervalScheduledTaskAgentProvider mockAgentProvider;
  @Mock private IntervalScheduledTaskAgent mockAgent;
  @Mock private List<IntervalScheduledTaskAgent> mockRegisteredAgents;
  @Mock private IntervalScheduledTask mockTask;
  private IntervalScheduledTaskProcessor subject;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    subject =
        new IntervalScheduledTaskProcessor(
            mockRegisteredAgents,
            mockTimePeriodAnnotationProcessor,
            mockDelayCalculator,
            mockAgentProvider);
  }

  @Test
  public void testProcessRawAndWrap() throws NoSuchMethodException {
    when(mockTimePeriodAnnotationProcessor.process(Mockito.any())).thenReturn(1L);
    when(mockDelayCalculator.process(Mockito.any()))
        .thenReturn(new IntervalScheduledTime(10000, 10000));

    IntervalScheduledTask result =
        subject.processRawAndWrap(
            ScheduledMethodWithAnnotatedFullTime.class,
            ScheduledMethodWithAnnotatedFullTime.class.getMethod("testRunnable"));

    verify(mockTimePeriodAnnotationProcessor).process(Mockito.any());
    verify(mockDelayCalculator).process(Mockito.any());
    assertEquals(10000, result.getDelayToStart());
    assertEquals(10000, result.getDelayToShutdown());
    assertEquals(1L, result.getPeriod());
    assertNotNull(result.getRunnable());
    assertEquals(TimeUnit.SECONDS, result.getTimeUnit());
  }

  @Test
  public void testSchedule() {
    when(mockAgentProvider.get()).thenReturn(mockAgent);

    subject.schedule(mockTask);

    verify(mockAgentProvider).get();
    verify(mockAgent).submit(mockTask);
  }

  @Test
  public void testIsMethodAValidScheduleAndSuccess() throws NoSuchMethodException {
    assertTrue(
        subject.isMethodAValidSchedule(
            ScheduledMethodWithAnnotatedFullTime.class.getMethod("testRunnable")));
  }

  @Test
  public void testIsMethodAValidScheduleAndFailure() throws NoSuchMethodException {
    assertFalse(
        subject.isMethodAValidSchedule(
            TestAnnotatedWithScheduledFrom.class.getMethod("testRunnable")));
  }

  static class ScheduledMethodWithAnnotatedFullTime {

    @ScheduledFrom(time = "01:11:22")
    @ScheduledUntil(time = "01:11:23")
    public Runnable testRunnable() {
      return () -> System.out.println("evil");
    }
  }

  static class TestAnnotatedWithScheduledFrom {

    @ScheduledFrom(time = "00:00:00")
    public Runnable testRunnable() {
      return () -> System.out.println("evil");
    }
  }
}
