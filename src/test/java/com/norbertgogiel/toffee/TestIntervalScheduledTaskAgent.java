package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.time.LocalTime;
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
    public void testScheduleTask() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(2);
        IntervalScheduledTask task = new IntervalScheduledTask(
                atomicInteger::getAndIncrement,
                0,
                100,
                1000,
                TimeUnit.MILLISECONDS
        );
        subject.submit(task);
        Thread.sleep(350);
        assertEquals(4, atomicInteger.get());
    }

    @Test
    public void testIntervalScheduleTask() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(1);
        IntervalScheduledTask task = new IntervalScheduledTask(
                atomicInteger::getAndIncrement,
                0,
                100,
                150,
                TimeUnit.MILLISECONDS
        );
        subject.submit(task);
        Thread.sleep(350);
        assertEquals(2, atomicInteger.get());
    }

    @Test
    public void testTimeScheduledTask() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntervalScheduledTaskAgent subject = new IntervalScheduledTaskAgent(1);
        LocalTime localTimeNow = LocalTime.now();
        LocalTime startTime = LocalTime.of(
                localTimeNow.getHour(),
                localTimeNow.getMinute(),
                localTimeNow.getSecond() + 1,
                localTimeNow.getNano());
        LocalTime endTime = LocalTime.of(
                localTimeNow.getHour(),
                localTimeNow.getMinute(),
                localTimeNow.getSecond() + 2,
                localTimeNow.getNano() - 100
        );
        subject.submit(
                atomicInteger::getAndIncrement,
                100,
                TimeUnit.MILLISECONDS,
                startTime,
                endTime);
        Thread.sleep(3000);
        assertEquals(10, atomicInteger.get());
    }
}
