package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }
}
