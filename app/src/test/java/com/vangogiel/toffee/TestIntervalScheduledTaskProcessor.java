package com.vangogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestIntervalScheduledTaskProcessor {

  @Mock private TimePeriodAnnotationProcessor mockTimePeriodAnnotationProcessor;
  @Mock private IntervalScheduledAnnotationProcessor mockDelayCalculator;
  @Mock private IntervalScheduledTaskAgentProvider mockAgentProvider;
  @Mock private IntervalScheduledTaskAgent mockAgent;
  @Mock private List<IntervalScheduledTaskAgent> mockRegisteredAgents;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(mockTimePeriodAnnotationProcessor.process(Mockito.any())).thenReturn(1L);
  }

  @Test
  public void testScheduled() {
    when(mockDelayCalculator.process(Mockito.any()))
        .thenReturn(new IntervalScheduledTime(10000, 10000));
    when(mockAgentProvider.get()).thenReturn(mockAgent);
    IntervalScheduledTaskProcessor subject =
        new IntervalScheduledTaskProcessor(
            mockRegisteredAgents,
            mockTimePeriodAnnotationProcessor,
            mockDelayCalculator,
            mockAgentProvider);

    assertDoesNotThrow(
        () ->
            subject.tryScheduleTask(
                ScheduledMethodWithAnnotatedFullTime.class,
                ScheduledMethodWithAnnotatedFullTime.class.getMethod("testRunnable")));

    verify(mockTimePeriodAnnotationProcessor).process(Mockito.any());
    verify(mockDelayCalculator).process(Mockito.any());
    verify(mockAgentProvider).get();
    verify(mockAgent).submit(Mockito.any());
    verify(mockRegisteredAgents).add(Mockito.any());
  }

  @Test
  public void testAnnotatedCorrectly() {
    when(mockAgentProvider.get()).thenReturn(mockAgent);
    IntervalScheduledTaskProcessor subject =
        new IntervalScheduledTaskProcessor(
            mockRegisteredAgents,
            mockTimePeriodAnnotationProcessor,
            mockDelayCalculator,
            mockAgentProvider);
    assertDoesNotThrow(
        () ->
            subject.tryScheduleTask(
                TestAnnotatedCorrectly.class,
                TestAnnotatedCorrectly.class.getMethod("testRunnable")));
    verify(mockAgentProvider).get();
  }

  @Test
  public void testAnnotatedWithScheduledFrom() {
    when(mockAgentProvider.get()).thenReturn(mockAgent);
    IntervalScheduledTaskProcessor subject =
        new IntervalScheduledTaskProcessor(
            mockRegisteredAgents,
            mockTimePeriodAnnotationProcessor,
            mockDelayCalculator,
            mockAgentProvider);
    assertDoesNotThrow(
        () ->
            subject.tryScheduleTask(
                TestAnnotatedWithScheduledFrom.class,
                TestAnnotatedWithScheduledFrom.class.getMethod("testRunnable")));
    verifyZeroInteractions(mockAgentProvider);
  }

  @Test
  public void testAnnotatedWithScheduledUntil() {
    when(mockAgentProvider.get()).thenReturn(mockAgent);
    IntervalScheduledTaskProcessor subject =
        new IntervalScheduledTaskProcessor(
            mockRegisteredAgents,
            mockTimePeriodAnnotationProcessor,
            mockDelayCalculator,
            mockAgentProvider);
    assertDoesNotThrow(
        () ->
            subject.tryScheduleTask(
                TestAnnotatedWithScheduledUntil.class,
                TestAnnotatedWithScheduledUntil.class.getMethod("testRunnable")));
    verifyZeroInteractions(mockAgentProvider);
  }

  static class ScheduledMethodWithAnnotatedFullTime {

    @ScheduledFrom(time = "01:11:22")
    @ScheduledUntil(time = "01:11:23")
    public Runnable testRunnable() {
      return () -> System.out.println("evil");
    }
  }

  static class ScheduledMethodThrowsException {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "23:59:59")
    public void testRunnable() throws Exception {
      throw new Exception("Evil");
    }
  }

  static class TestAnnotatedCorrectly {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "01:01:01:")
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

  static class TestAnnotatedWithScheduledUntil {

    @ScheduledUntil(time = "01:01:01:")
    public Runnable testRunnable() {
      return () -> System.out.println("evil");
    }
  }

  static class TestAnnotatedAnnotatedCorrectlyNotRunnable {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "01:01:01:")
    public void testRunnable() {}
  }
}
