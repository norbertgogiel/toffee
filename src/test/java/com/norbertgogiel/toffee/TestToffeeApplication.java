package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Test;

import java.io.IOException;
import java.util.IllegalFormatPrecisionException;
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
        counter = new AtomicInteger(0);
        assertDoesNotThrow(() -> subject.init(IntervalScheduledTestClassAndMethodWithAnnotatedFullTime.class));
        assertEquals(1, subject.getTotalCorePoolSize());
        assertEquals(1, subject.getTotalCurrentPoolSize());
        assertEquals(0, subject.getTotalCurrentTaskCount());
        assertEquals(0, counter.get());
    }

    @Test
    public void testInitAnnotatedClassAndMethodWithIncorrectTime() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(IntervalScheduledTestClassAndMethodIncorrectlyAnnotated.class));
    }

    @Test
    public void testInitAnnotatedClassAndMethodWithHourOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(IntervalScheduledTestClassAndMethodAnnotatedWithHourOutOfRange.class));
    }

    @Test
    public void testInitAnnotatedClassAndMethodWithMinuteOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(IntervalScheduledTestClassAndMethodAnnotatedWithMinuteOutOfRange.class));
    }

    @Test
    public void testInitAnnotatedClassAndMethodWithSecondOutOfRange() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(IllegalFormatPrecisionException.class, () -> subject.init(IntervalScheduledTestClassAndMethodAnnotatedWithSecondOutOfRange.class));
    }

    @IntervalScheduled
    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodWithAnnotatedFullTime {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() throws IOException {
            return counter::getAndIncrement;
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodIncorrectlyAnnotated {

        @ScheduledFrom(time = "01")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() throws IOException {
            return counter::getAndIncrement;
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodAnnotatedWithHourOutOfRange {

        @ScheduledFrom(time = "24:11:22")
        @ScheduledUntil(time = "02:11:22")
        public Runnable testRunnable() throws IOException {
            return counter::getAndIncrement;
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodAnnotatedWithMinuteOutOfRange {

        @ScheduledFrom(time = "01:11:22")
        @ScheduledUntil(time = "02:60:22")
        public Runnable testRunnable() throws IOException {
            return counter::getAndIncrement;
        }
    }

    @IntervalScheduled
    static class IntervalScheduledTestClassAndMethodAnnotatedWithSecondOutOfRange {

        @ScheduledFrom(time = "01:11:60")
        @ScheduledUntil(time = "24:11:22")
        public Runnable testRunnable() throws IOException {
            return counter::getAndIncrement;
        }
    }
}
