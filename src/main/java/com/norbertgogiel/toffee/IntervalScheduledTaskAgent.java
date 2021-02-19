package com.norbertgogiel.toffee;


import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class IntervalScheduledTaskAgent {

    private ScheduledThreadPoolExecutor taskAgent;
    private ScheduledThreadPoolExecutor shutdownAgent;

    public IntervalScheduledTaskAgent(int corePoolSize) {
        taskAgent = new ScheduledThreadPoolExecutor(corePoolSize);
        shutdownAgent = new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public int getCorePoolSize() {
        return taskAgent.getCorePoolSize();
    }

    public int getCurrentPoolSize() {
        return taskAgent.getPoolSize();
    }

    public int getCurrentTaskCount() {
        return taskAgent.getActiveCount();
    }

    public void submit(Runnable runnable,
                       long initDelay,
                       long period,
                       long delayToShutdown,
                       TimeUnit timeUnit) {
        Future<?> future =  taskAgent.scheduleAtFixedRate(runnable, initDelay, period, timeUnit);
        shutdownAgent.schedule(() -> future.cancel(false), delayToShutdown, timeUnit);
    }

    public void submit(Runnable runnable,
                       long period,
                       TimeUnit timeUnit,
                       LocalTime startTime,
                       LocalTime endTime) {
        long startDelay = Duration.between(LocalTime.now(), startTime).toMillis();
        long endDelay = Duration.between(LocalTime.now(), endTime).toMillis();
        Future<?> future =  taskAgent.scheduleAtFixedRate(runnable, startDelay, period, timeUnit);
        shutdownAgent.schedule(() -> future.cancel(false), endDelay, timeUnit);
    }
}
