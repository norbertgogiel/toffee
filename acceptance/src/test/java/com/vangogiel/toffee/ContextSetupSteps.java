package com.vangogiel.toffee;

import io.cucumber.java.en.Given;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Context setup steps for setting up context per scenario.
 */
public class ContextSetupSteps {

    public static AtomicInteger atomicInteger = new AtomicInteger();
    public static ToffeeContext toffeeContext;

    @Given("a task set to run non-stop with every second custom annotation")
    public void basicContextWithContinuousTask() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeCustomEverySecond.class);
    }

    @Given("a task set to run non-stop with every 3-seconds annotation")
    public void basicContextWithContinuousTaskEvery3Seconds() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEvery3Seconds.class);
    }

    @Given("a task set to run non-stop with every second annotation")
    public void basicContextWithEverySecondPeriodAnnotation() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEverySecond.class);
    }

    @Given("a task set to run non-stop with every minute annotation")
    public void basicContextWithEveryMinutePeriodAnnotation() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEveryMinute.class);
    }

    @Given("a task set to run non-stop with every hour annotation")
    public void basicContextWithEveryHourPeriodAnnotation() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEveryHour.class);
    }

    @Given("a task that is set to run in the future before midnight")
    public void basicContextFutureTaskBeforeMidnight() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunInTheFutureBeforeMidnight.class);
    }

    @Given("a task that is set to run in the future after midnight")
    public void basicContextFutureTaskAfterMidnight() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunInTheFutureAfterMidnight.class);
    }

    @Given("a task that is set to run in the future over midnight")
    public void basicContextFutureTaskOverMidnight() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunInTheFutureOverMidnight.class);
    }

    @Given("two tasks scheduled to run all the time at different rates")
    public void basicContextWithTwoTasks() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(
                TestClasses.IntervalScheduledToRunAllTheTimeCustomEverySecond.class,
                TestClasses.IntervalScheduledToRunAllTheTimeEvery3Seconds.class
        );
    }

    @Given("five tasks scheduled to run at different times and at different rates")
    public void basicContextWithFiveTasks() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(
                TestClasses.IntervalScheduledToRunAllTheTimeCustomEverySecond.class,
                TestClasses.IntervalScheduledToRunAllTheTimeEvery3Seconds.class,
                TestClasses.IntervalScheduledToRunAllTheTimeCustomEveryMinute.class,
                TestClasses.IntervalScheduledToRunAllTheTimeCustomEveryHour.class,
                TestClasses.IntervalScheduledToRunInTheFutureAfterMidnight.class
        );
    }
}
