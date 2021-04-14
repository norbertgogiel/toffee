package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.norbertgogiel.toffee.annotations.Every;
import com.norbertgogiel.toffee.annotations.EveryHour;
import com.norbertgogiel.toffee.annotations.EveryMinute;
import com.norbertgogiel.toffee.annotations.EverySecond;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class TestTimePeriodAnnotationProcessor {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testScheduledAtEverySecond() throws NoSuchMethodException {
        TimePeriodAnnotationProcessor subject = new TimePeriodAnnotationProcessor();

        long result = subject.process(
                ScheduledMethodAnnotatedAtEverySecond.class.getMethod("testRunnable")
        );

        assertEquals(1, result);
    }

    @Test
    public void testIScheduledAtEveryMinute() throws NoSuchMethodException {
        TimePeriodAnnotationProcessor subject = new TimePeriodAnnotationProcessor();

        long result = subject.process(
                ScheduledMethodAnnotatedAtEveryMinute.class.getMethod("testRunnable")
        );

        assertEquals(60, result);
    }

    @Test
    public void testScheduledAtVeryHour() throws NoSuchMethodException {
        TimePeriodAnnotationProcessor subject = new TimePeriodAnnotationProcessor();

        long result = subject.process(
                ScheduledMethodAnnotatedAtEveryHour.class.getMethod("testRunnable")
        );

        assertEquals(3600, result);
    }

    @Test
    public void testScheduledAtVery2Seconds() throws NoSuchMethodException {
        TimePeriodAnnotationProcessor subject = new TimePeriodAnnotationProcessor();

        long result = subject.process(
                ScheduledMethodAnnotatedAtEvery2Seconds.class.getMethod("testRunnable")
        );

        assertEquals(2, result);
    }

    @Test
    public void testNoAnnotation() throws NoSuchMethodException {
        TimePeriodAnnotationProcessor subject = new TimePeriodAnnotationProcessor();

        long result = subject.process(
                NotAnnotated.class.getMethod("testRunnable")
        );

        assertEquals(1, result);
    }

    static class ScheduledMethodAnnotatedAtEverySecond {

        @EverySecond
        public void testRunnable() {
        }
    }

    static class ScheduledMethodAnnotatedAtEveryMinute {

        @EveryMinute
        public void testRunnable() {
        }
    }

    static class ScheduledMethodAnnotatedAtEveryHour {

        @EveryHour
        public void testRunnable() {
        }
    }

    static class ScheduledMethodAnnotatedAtEvery2Seconds {

        @Every(period = 2, timeUnit = TimeUnit.SECONDS)
        public void testRunnable() {
        }
    }

    static class NotAnnotated {

        public void testRunnable() {
        }
    }
}
