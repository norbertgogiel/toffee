package com.norbertgogiel.toffee;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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

    public void submit(IntervalScheduledTask task) {
        Future<?> future = taskAgent.scheduleAtFixedRate(
                task.getRunnable(),
                task.getDelayToStart(),
                task.getPeriod(),
                task.getTimeUnit()
        );
        shutdownAgent.schedule(
                () -> future.cancel(false),
                task.getDelayToShutdown(),
                task.getTimeUnit()
        );
    }
}
