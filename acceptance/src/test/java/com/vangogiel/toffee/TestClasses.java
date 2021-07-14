package com.vangogiel.toffee;

import com.vangogiel.toffee.annotations.Every;
import com.vangogiel.toffee.annotations.EveryHour;
import com.vangogiel.toffee.annotations.EveryMinute;
import com.vangogiel.toffee.annotations.EverySecond;
import com.vangogiel.toffee.annotations.ScheduledFrom;
import com.vangogiel.toffee.annotations.ScheduledUntil;
import com.vangogiel.toffee.annotations.Weekdays;

import java.util.concurrent.TimeUnit;

/**
 * Classes containing test subjects for BDD scenarios.
 */
public class TestClasses {

    static class IntervalScheduledToRunAllTheTimeEvery3Seconds {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 3, timeUnit = TimeUnit.SECONDS)
        public void getSimpleScheduleWith3SecondPeriod() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeCustomEverySecond {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 1, timeUnit = TimeUnit.SECONDS)
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeCustomEveryMinute {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 1, timeUnit = TimeUnit.MINUTES)
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeCustomEveryHour {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @Every(period = 1, timeUnit = TimeUnit.HOURS)
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeEverySecond {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeEveryMinute {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EveryMinute
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunAllTheTimeEveryHour {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EveryHour
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureBeforeMidnight {

        @ScheduledFrom(time = "23:59:58")
        @ScheduledUntil(time = "23:59:59")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureAfterMidnight {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "00:00:10")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunInTheFutureOverMidnight{

        @ScheduledFrom(time = "23:58:59")
        @ScheduledUntil(time = "00:00:10")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunOnMondays {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        @Weekdays(days = "Mon")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunOnTuesdays {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        @Weekdays(days = "Tue")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunOnMultipleDaysAhead {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        @Weekdays(days = "Mon,Wed,Fri")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }

    static class IntervalScheduledToRunOnMultipleDays {

        @ScheduledFrom(time = "00:00:00")
        @ScheduledUntil(time = "23:59:59")
        @EverySecond
        @Weekdays(days = "Tue,Wed,Fri")
        public void getSimpleSchedule() {
            ContextSetupSteps.atomicInteger.getAndIncrement();
        }
    }
}
