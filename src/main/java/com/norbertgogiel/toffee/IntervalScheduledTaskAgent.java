package com.norbertgogiel.toffee;


import java.util.concurrent.ScheduledThreadPoolExecutor;

public class IntervalScheduledTaskAgent {

    private ScheduledThreadPoolExecutor executor;

    public IntervalScheduledTaskAgent(int corePoolSize) {
        executor = new ScheduledThreadPoolExecutor(corePoolSize);
    }
}
