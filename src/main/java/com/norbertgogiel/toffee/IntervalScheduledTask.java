package com.norbertgogiel.toffee;

import java.util.concurrent.TimeUnit;

public class IntervalScheduledTask {

    private final Runnable runnable;
    private final long initDelay;
    private final long period;
    private final long delayToShutdown;
    private final TimeUnit timeUnit;

    public IntervalScheduledTask(Runnable runnable,
                                 long initDelay,
                                 long period,
                                 long delayToShutdown,
                                 TimeUnit timeUnit) {
        this.runnable = runnable;
        this.initDelay = initDelay;
        this.period = period;
        this.delayToShutdown = delayToShutdown;
        this.timeUnit = timeUnit;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public long getInitDelay() {
        return initDelay;
    }

    public long getPeriod() {
        return period;
    }

    public long getDelayToShutdown() {
        return delayToShutdown;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
