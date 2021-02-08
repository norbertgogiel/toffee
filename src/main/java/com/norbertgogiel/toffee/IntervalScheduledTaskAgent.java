package com.norbertgogiel.toffee;


import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntervalScheduledTaskAgent {

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

    public void submit(Runnable runnable, long initDelay, long period, long delayToShutdown, TimeUnit timeUnit) {
        Future<?> future =  taskAgent.scheduleAtFixedRate(runnable, initDelay, period, timeUnit);
        shutdownAgent.schedule(() -> future.cancel(false), delayToShutdown, timeUnit);
    }
}
