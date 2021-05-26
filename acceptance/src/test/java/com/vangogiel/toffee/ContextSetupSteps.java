package com.vangogiel.toffee;

import io.cucumber.java.en.Given;

import java.util.concurrent.atomic.AtomicInteger;

public class ContextSetupSteps {

    public static AtomicInteger atomicInteger = new AtomicInteger();
    public static ToffeeContext toffeeContext;

    @Given("a task that is set to run all the time every second")
    public void basicContextWithContinuousTask() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEverySecond.class);
    }

    @Given("a task that is set to run all the time every 3 seconds")
    public void basicContextWithContinuousTaskEvery3Seconds() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(TestClasses.IntervalScheduledToRunAllTheTimeEvery3Seconds.class);
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
                TestClasses.IntervalScheduledToRunAllTheTimeEverySecond.class,
                TestClasses.IntervalScheduledToRunAllTheTimeEvery3Seconds.class
        );
    }
}
