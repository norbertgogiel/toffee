package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import java.io.IOException;

public class TestToffeeContext {

    @Test
    public void testDefaultConstructor() {
        assertDoesNotThrow(ToffeeContext::new);
    }

    @Test
    public void testIncludeObject() {
        ToffeeContext subject = new ToffeeContext();
        assertDoesNotThrow(() -> subject.include(TestToffeeContext.class));
    }

    @Test
    public void testIncludeNullObjectAndThrow() {
        ToffeeContext subject = new ToffeeContext();
        assertThrows(IllegalArgumentException.class, () -> subject.include(null));
    }

    @Test
    public void testGetTotalCorePoolSize() {
        ToffeeContext subject = new ToffeeContext();
        assertEquals(0, subject.getTotalCorePoolSize());
    }

    @Test
    public void testGetTotalCurrentPoolSize() {
        ToffeeContext subject = new ToffeeContext();
        assertEquals(0, subject.getTotalCurrentPoolSize());
    }

    @Test
    public void testIncludeIntervalScheduledNonNullClassWithMethod() {
        ToffeeContext subject = new ToffeeContext();
        assertDoesNotThrow(() -> subject.include(IntervalScheduledTestClass.class));
    }

    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }
}
