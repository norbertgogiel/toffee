package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class TestToffeeApplication {

    private AtomicInteger counter;

    @Test
    public void testDefaultConstructor() {
        assertDoesNotThrow(ToffeeApplication::new);
    }

    @Test
    public void testInitObject() {
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(TestToffeeApplication.class));
    }

    @Test
    public void testInitNullObjectAndThrows() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalArgumentException.class, () -> subject.init(null));
    }

    @Test
    public void testGetTotalCorePoolSize() {
        ToffeeApplication subject = new ToffeeApplication();
        assertEquals(0, subject.getTotalCorePoolSize());
    }

    @Test
    public void testGetTotalCurrentPoolSize() {
        ToffeeApplication subject = new ToffeeApplication();
        assertEquals(0, subject.getTotalCurrentPoolSize());
    }

    @Test
    public void testGetTotalCurrentTaskCount() {
        ToffeeApplication subject = new ToffeeApplication();
        assertEquals(0, subject.getTotalCurrentTaskCount());
    }

    @Test
    public void testInitIntervalScheduledAnnotatedClass() {
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(IntervalScheduledTestClass.class));
    }

    @Test
    public void testInitScheduledAnnotatedClassAndMethodWithAnnotatedFullTime() {
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(IntervalScheduledTestClassAndMethodWithAnnotatedFullTime.class));
    }

    @IntervalScheduled
    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodWithAnnotatedFullTime {

        @ScheduledFrom(hour = 11, minute = 12, second = 13, nano = 14)
        @ScheduledUntil(hour = 12, minute = 13, second = 14, nano = 15)
        public Runnable testRunnable() throws IOException {
            return () -> System.out.println("I am a teapot");
        }
    }
}
