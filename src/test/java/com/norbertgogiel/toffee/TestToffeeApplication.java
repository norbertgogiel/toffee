package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.norbertgogiel.toffee.annotations.Every;
import com.norbertgogiel.toffee.annotations.EveryHour;
import com.norbertgogiel.toffee.annotations.EveryMinute;
import com.norbertgogiel.toffee.annotations.EverySecond;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Test;

import java.io.IOException;
import java.sql.Time;
import java.util.IllegalFormatPrecisionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestToffeeApplication {

    private static AtomicInteger counter;

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
    public void testInitIntervalScheduledNonNullClassWithMethod() {
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(IntervalScheduledTestClass.class));
    }

    @Test
    public void testInitScheduledMethodWithAnnotatedFullTime() {
        ToffeeApplication subject = new ToffeeApplication();
        counter = new AtomicInteger(0);
        assertDoesNotThrow(() -> subject.init(ScheduledMethodWithAnnotatedFullTime.class));
        assertEquals(1, subject.getTotalCorePoolSize());
        assertEquals(1, subject.getTotalCurrentPoolSize());
        assertEquals(0, counter.get());
    }

    @Test
    public void testInitTwoScheduledMethods() {
        ToffeeApplication subject = new ToffeeApplication();
        counter = new AtomicInteger(0);
        assertDoesNotThrow(() -> subject.init(TwoScheduledMethodsWithAnnotatedFullTime.class));
        assertEquals(2, subject.getTotalCorePoolSize());
        assertEquals(2, subject.getTotalCurrentPoolSize());
        assertEquals(0, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodWithIncorrectTime() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(ScheduledMethodIncorrectlyAnnotated.class));
    }

    @Test
    public void testInitAnnotatedMethodWithHourOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(ScheduledMethodAnnotatedWithHourOutOfRange.class));
    }

    @Test
    public void testInitAnnotatedMethodWithMinuteOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(ScheduledMethodAnnotatedWithMinuteOutOfRange.class));
    }

    @Test
    public void testInitAnnotatedMethodWithSecondOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(ScheduledMethodAnnotatedWithSecondOutOfRange.class));
    }

    @Test
    public void testInitAnnotatedMethodDuringSchedule() throws InterruptedException {
        counter = new AtomicInteger();
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(ScheduledMethodAnnotated24Hrs.class));
        Thread.sleep(1300);
        assertEquals(1, subject.getTotalCorePoolSize());
        assertEquals(1, subject.getTotalCurrentPoolSize());
        assertTrue(counter.get() > 0);
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtEverySecond() throws InterruptedException {
        counter = new AtomicInteger();
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(ScheduledMethodAnnotatedAtEverySecond.class));
        Thread.sleep(1900);
        assertEquals(2, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtEveryMinute() throws InterruptedException {
        counter = new AtomicInteger();
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(ScheduledMethodAnnotatedAtEveryMinute.class));
        Thread.sleep(1500);
        assertEquals(1, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtVeryHour() throws InterruptedException {
        counter = new AtomicInteger();
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(ScheduledMethodAnnotatedAtEveryHour.class));
        Thread.sleep(1500);
        assertEquals(1, counter.get());
    }

    @Test
    public void testInitAnnotatedMethodScheduledAtVery2Seconds() throws InterruptedException {
        counter = new AtomicInteger();
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(ScheduledMethodAnnotatedAtEvery2Seconds.class));
        Thread.sleep(1500);
        assertEquals(1, counter.get());
        Thread.sleep(1600);
        assertEquals(2, counter.get());
    }

    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }

    static class ScheduledMethodWithAnnotatedFullTime {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class TwoScheduledMethodsWithAnnotatedFullTime {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnableOne() {
            return () -> System.out.println("method1");
        }

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnableTwo() {
            return () -> System.out.println("method2");
        }
    }

    static class ScheduledMethodAnnotated24Hrs {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodIncorrectlyAnnotated {

        @ScheduledFrom(time = "01")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedWithHourOutOfRange {

        @ScheduledFrom(time = "24:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedWithMinuteOutOfRange {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:60:22")
        public Runnable testRunnable() {
            return counter::getAndIncrement;
        }
    }

    static class ScheduledMethodAnnotatedWithSecondOutOfRange {

        @ScheduledFrom(time = "01:11:60")
        @ScheduledUntil(time = "24:11:22")
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
