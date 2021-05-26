package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;

import java.util.concurrent.TimeUnit;

public class IntervalScheduledToRunAllTheTimeEverySecond {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "23:59:59")
    @Every(period = 1, timeUnit = TimeUnit.SECONDS)
    public void getSimpleSchedule() {
        ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
    }
}
