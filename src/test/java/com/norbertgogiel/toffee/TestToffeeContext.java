package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.IOException;

public class TestToffeeContext {

    @Test
    public void testDefaultConstructor() {
        assertDoesNotThrow((ThrowingSupplier<ToffeeContext>) ToffeeContext::new);
    }

    @Test
    public void testIncludeObject() {
        assertDoesNotThrow(() -> new ToffeeContext(TestToffeeContext.class));
    }

    @Test
    public void testIncludeNullObjectAndThrow() {
        assertThrows(IllegalArgumentException.class, () -> new ToffeeContext((Class<?>) null));
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
        assertDoesNotThrow(() -> new ToffeeContext(IntervalScheduledTestClass.class));
    }

    @Test
    public void testInitWithMoreThanOneClass() {
        assertDoesNotThrow(() ->
                new ToffeeContext(
                IntervalScheduledTestClass.class,
                AlternativeIntervalScheduledTestClass.class
                )
        );
    }

    static class IntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }

    static class AlternativeIntervalScheduledTestClass {

        public Runnable testRunnable() throws IOException {
            throw new IOException("evil");
        }
    }
}
