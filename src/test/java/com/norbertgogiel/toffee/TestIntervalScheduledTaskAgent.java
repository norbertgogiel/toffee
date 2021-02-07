package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Test
    public void testScheduleTask() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
        subject.submit(atomicInteger::getAndIncrement, 0, 100, TimeUnit.MILLISECONDS);
        Thread.sleep(350);
        assertEquals(4, atomicInteger.get());
    }
}
