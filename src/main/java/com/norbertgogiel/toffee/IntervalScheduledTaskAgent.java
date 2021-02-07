package com.norbertgogiel.toffee;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntervalScheduledTaskAgent {

    private ScheduledThreadPoolExecutor taskAgent;

    public IntervalScheduledTaskAgent(int corePoolSize) {
        taskAgent = new ScheduledThreadPoolExecutor(corePoolSize);
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

    public void submit(Runnable runnable, long initDelay, long delay, TimeUnit timeUnit) {
        taskAgent.scheduleAtFixedRate(runnable, initDelay, delay, timeUnit);
    }
}
