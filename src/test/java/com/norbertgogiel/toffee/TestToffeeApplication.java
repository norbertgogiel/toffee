package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;
import org.junit.Test;

import java.io.IOException;

public class TestToffeeApplication {

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
    public void testInitIntervalScheduledAnnotatedClass() {
        ToffeeApplication subject = new ToffeeApplication();
        assertDoesNotThrow(() -> subject.init(IntervalScheduledClassTest.class));
    }

    @Test
    public void testInitScheduledAnnotatedClassAndMethods() {
        ToffeeApplication subject = new ToffeeApplication();
        assertThrows(UnsupportedOperationException.class ,() -> subject.init(IntervalScheduledClassAndMethodsTest.class));
    }

    @IntervalScheduled
    static class IntervalScheduledClassTest {

        public void testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }

    @IntervalScheduled
    static class IntervalScheduledClassAndMethodsTest {

        @ScheduledFrom(hour = 11, minute = 12, second = 13, nano = 14)
        @ScheduledUntil(hour = 12, minute = 13, second = 14, nano = 15)
        public void testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }
}
