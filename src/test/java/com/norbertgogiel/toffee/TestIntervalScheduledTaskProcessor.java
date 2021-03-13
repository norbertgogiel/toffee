package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.Every;
import com.norbertgogiel.toffee.annotations.EveryHour;
import com.norbertgogiel.toffee.annotations.EveryMinute;
import com.norbertgogiel.toffee.annotations.EverySecond;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIntervalScheduledTaskProcessor {

    @Mock
    private List<IntervalScheduledTaskAgent> mockRegisteredAgents;
    @Mock
    private TimeParser mockTimeParser;
    private static AtomicInteger counter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitScheduledMethodWithAnnotatedFullTime() {
        counter = new AtomicInteger(0);
        List<IntervalScheduledTaskAgent> registeredAgents = new ArrayList<>();
        Mockito.when(mockTimeParser.validateAndParse("01:11:22")).thenReturn(LocalTime.of(1,11,22));
        Mockito.when(mockTimeParser.validateAndParse("01:11:23")).thenReturn(LocalTime.of(1,11,23));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(registeredAgents, mockTimeParser);

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
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(registeredAgents, mockTimeParser);

        assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotated24Hrs.class,
                ScheduledMethodAnnotated24Hrs.class.getMethod("testRunnable")
        ));

        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCorePoolSize).sum());
        assertEquals(1, registeredAgents.stream().mapToInt(IntervalScheduledTaskAgent::getCurrentPoolSize).sum());
        assertTrue(counter.get() > 0);
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtEverySecond() throws InterruptedException {
        counter = new AtomicInteger(0);
        Mockito.when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        Mockito.when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23,59,59));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(mockRegisteredAgents, mockTimeParser);

        assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotatedAtEverySecond.class,
                ScheduledMethodAnnotatedAtEverySecond.class.getMethod("testRunnable")
        ));

        Thread.sleep(1900);
        assertEquals(2, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtEveryMinute() throws InterruptedException {
        counter = new AtomicInteger(0);
        Mockito.when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        Mockito.when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23,59,59));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(mockRegisteredAgents, mockTimeParser);

        assertDoesNotThrow(() -> assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotatedAtEveryMinute.class,
                ScheduledMethodAnnotatedAtEveryMinute.class.getMethod("testRunnable")
        )));

        Thread.sleep(1500);
        assertEquals(1, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtVeryHour() throws InterruptedException {
        counter = new AtomicInteger(0);
        Mockito.when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        Mockito.when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23,59,59));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(mockRegisteredAgents, mockTimeParser);

        assertDoesNotThrow(() -> assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotatedAtEveryHour.class,
                ScheduledMethodAnnotatedAtEveryHour.class.getMethod("testRunnable")
        )));

        Thread.sleep(1500);
        assertEquals(1, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtVery2Seconds() throws InterruptedException {
        counter = new AtomicInteger(0);
        Mockito.when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        Mockito.when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23,59,59));
        IntervalScheduledTaskProcessor subject = new IntervalScheduledTaskProcessor(mockRegisteredAgents, mockTimeParser);

        assertDoesNotThrow(() -> assertDoesNotThrow(() -> subject.scheduleTask(
                ScheduledMethodAnnotatedAtEvery2Seconds.class,
                ScheduledMethodAnnotatedAtEvery2Seconds.class.getMethod("testRunnable")
        )));

        Thread.sleep(1500);
        assertEquals(1, counter.get());
        Thread.sleep(1600);
        assertEquals(2, counter.get());
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

    static class ScheduledMethodAnnotatedAtEverySecond {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedAtEveryMinute {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EveryMinute
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedAtEveryHour {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EveryHour
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedAtEvery2Seconds {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 2, timeUnit = TimeUnit.SECONDS)
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }
}
