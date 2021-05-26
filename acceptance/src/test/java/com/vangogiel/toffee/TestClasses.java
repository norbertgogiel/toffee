package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;

import java.util.concurrent.TimeUnit;

public class TestClasses {

    static class IntervalScheduledToRunAllTheTimeEvery3Seconds {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 3, timeUnit = TimeUnit.SECONDS)
        public void getSimpleScheduleWith3SecondPeriod() {
            ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeEverySecond {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 1, timeUnit = TimeUnit.SECONDS)
        public void getSimpleSchedule() {
            ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureBeforeMidnight {

        @ScheduledFrom(time = "23:59:58")
        @ScheduledUntil(time = "23:59:59")
        public void getSimpleSchedule() {
            ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureAfterMidnight {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "00:00:10")
        public void getSimpleSchedule() {
            ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureOverMidnight{

        @ScheduledFrom(time = "23:58:59")
        @ScheduledUntil(time = "00:00:10")
        public void getSimpleSchedule() {
            ScheduledAnnotationSpecSteps.atomicInteger.getAndIncrement();
        }
    }
}
