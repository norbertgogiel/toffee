package com.norbertgogiel.toffee;


import java.util.concurrent.ScheduledThreadPoolExecutor;

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
}
