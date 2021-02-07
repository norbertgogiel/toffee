package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestIntervalScheduledTaskAgent {

    @Test
    public void testCreateInstanceOfIntervalScheduledTaskAgent() {
        assertDoesNotThrow(() -> new IntervalScheduledTaskAgent(1));
    }

    @Test
    public void testCorePoolSize() {
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
        assertEquals(2, subject.getCorePoolSize());
    }

    @Test
    public void testCurrentPoolSize() {
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
        assertEquals(0, subject.getCurrentPoolSize());
    }

    @Test
    public void testCurrentTasksCount() {
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
        assertEquals(0, subject.getCurrentTaskCount());
    }
}
