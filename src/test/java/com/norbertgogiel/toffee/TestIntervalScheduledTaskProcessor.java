package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIntervalScheduledTaskProcessor {

    @Mock
    private TimePeriodAnnotationProcessor mockTimePeriodAnnotationProcessor;
    @Mock
    private TimeParser mockTimeParser;
    private static AtomicInteger counter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockTimePeriodAnnotationProcessor.tryGetPeriodFromAnnotation(Mockito.any())).thenReturn(1L);
    }

    @Test
    public void testInitScheduledMethodWithAnnotatedFullTime() {
        counter = new AtomicInteger(0);
        List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
        Mockito.when(mockTimeParser.validateAndParse("01:11:22")).thenReturn(LocalTime.of(1,11,22));
        Mockito.when(mockTimeParser.validateAndParse("01:11:23")).thenReturn(LocalTime.of(1,11,23));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(
                registeredAgents,
                mockTimeParser,
                mockTimePeriodAnnotationProcessor
        );

        assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodWithAnnotatedFullTime.class,
                ScheduledMethodWithAnnotatedFullTime.class.getMethod("testRunnable")
        ));

        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum());
        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum());
        assertEquals(0, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodDuringSchedule() {
        counter = new AtomicInteger(0);
        List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
        Mockito.when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        Mockito.when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23,59,59));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(
                registeredAgents,
                mockTimeParser,
                mockTimePeriodAnnotationProcessor
        );

        assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotated24Hrs.class,
                ScheduledMethodAnnotated24Hrs.class.getMethod("testRunnable")
        ));

        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum());
        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum());
        assertTrue(counter.get() > 0);
    }

    static class ScheduledMethodWithAnnotatedFullTime {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "01:11:23")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotated24Hrs {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }
}
