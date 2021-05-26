package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;

import java.util.concurrent.TimeUnit;

public class IntervalScheduledToRunAllTheTimeEvery3Seconds {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "23:59:59")
    @Every(period = 3, timeUnit = TimeUnit.SECONDS)
    public void getSimpleScheduleWith3SecondPeriod() {
        ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
    }
}
