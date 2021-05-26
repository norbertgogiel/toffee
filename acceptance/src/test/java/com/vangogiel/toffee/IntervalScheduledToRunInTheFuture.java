package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;

public class IntervalScheduledToRunInTheFuture {

    @ScheduledFrom(time = "23:59:58")
    @ScheduledUntil(time = "23:59:59")
    public void getSimpleSchedule() {
        ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
    }
}
