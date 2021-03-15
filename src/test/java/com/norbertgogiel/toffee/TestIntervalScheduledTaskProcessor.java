package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestIntervalScheduledTaskProcessor {

    @Mock
    private TimePeriodAnnotationProcessor mockTimePeriodAnnotationProcessor;
    @Mock
    private IntervalScheduledAnnotationProcessor mockDelayCalculator;
    @Mock
    private IntervalScheduledTaskAgentProvider mockAgentProvider;
    @Mock
    private IntervalScheduledTaskAgent mockAgent;
    @Mock
    private List<IntervalScheduledTaskAgent> mockRegisteredAgents;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockTimePeriodAnnotationProcessor.process(Mockito.any())).thenReturn(1L);
    }

    @Test
    public void testScheduled() {
        when(mockDelayCalculator.process(Mockito.any())).thenReturn(
                new IntervalScheduledTime(10000, 10000));
        when(mockAgentProvider.get()).thenReturn(mockAgent);
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(
                mockRegisteredAgents,
                mockTimePeriodAnnotationProcessor,
                mockDelayCalculator,
                mockAgentProvider
        );

        assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodWithAnnotatedFullTime.class,
                ScheduledMethodWithAnnotatedFullTime.class.getMethod("testRunnable")
        ));

        verify(mockTimePeriodAnnotationProcessor).process(Mockito.any());
        verify(mockDelayCalculator).process(Mockito.any());
        verify(mockAgentProvider).get();
        verify(mockAgent).submit(Mockito.any());
        verify(mockRegisteredAgents).add(Mockito.any());
    }

    @Test
    public void testScheduledThrowsException() {
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(
                mockRegisteredAgents,
                mockTimePeriodAnnotationProcessor,
                mockDelayCalculator,
                mockAgentProvider
        );

        assertThrows(IllegalStateException.class, () -> subject.scheduleTask(
                ScheduledMethodThrowsException.class,
                ScheduledMethodThrowsException.class.getMethod("testRunnable")
        ));
        verifyZeroInteractions(mockAgentProvider);
        verifyZeroInteractions(mockAgent);
        verifyZeroInteractions(mockRegisteredAgents);
    }

    static class ScheduledMethodWithAnnotatedFullTime {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "01:11:23")
        public void testRunnable() {
        }
    }

    static class ScheduledMethodThrowsException {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        public Runnable testRunnable() throws Exception {
            throw new Exception("Evil");
        }
    }
}
