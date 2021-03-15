package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

public class TestIntervalScheduledAnnotationProcessor {

    @Mock
    private TimeParser mockTimeParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testScheduledInTheFuture() throws NoSuchMethodException {
        IntervalScheduledAnnotationProcessor subject = new IntervalScheduledAnnotationProcessor(mockTimeParser);
        when(mockTimeParser.validateAndParse("01:11:22")).thenReturn(LocalTime.of(1,11,22));
        when(mockTimeParser.validateAndParse("01:11:23")).thenReturn(LocalTime.of(1,11,23));

        IntervalScheduledTime result = subject.process(ScheduledInTheFuture.class.getMethod("testRunnable"));

        assertTrue(result.getDelayToStart() > 0);
        assertTrue(result.getDelayToShutdown() > 0);
        assertEquals(1, result.getDelayToShutdown() - result.getDelayToStart());
    }

    @Test
    public void testScheduledNow() throws NoSuchMethodException {
        IntervalScheduledAnnotationProcessor subject = new IntervalScheduledAnnotationProcessor(mockTimeParser);
        when(mockTimeParser.validateAndParse("00:00:00")).thenReturn(LocalTime.of(0,0,0));
        when(mockTimeParser.validateAndParse("23:59:59")).thenReturn(LocalTime.of(23, 59, 59));

        IntervalScheduledTime result = subject.process(ScheduledNow.class.getMethod("testRunnable"));

        assertEquals(0, result.getDelayToStart());
        assertTrue(result.getDelayToShutdown() > 0);
    }

    static class ScheduledInTheFuture {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "01:11:23")
        public void testRunnable() {
        }
    }

    static class ScheduledNow {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        public void testRunnable() {
        }
    }
}
