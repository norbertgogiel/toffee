package com.vangogiel.toffee;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class ScheduledAnnotationSpecSteps {

    static final AtomicInteger atomicInteger = new AtomicInteger();

    private ToffeeContext toffeeContext;

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

    @When("I wait {int} seconds")
    public void wait(int wait) throws InterruptedException {
        Thread.sleep(wait * 1000L);
    }

    @When("again I wait {int} seconds")
    public void waitAgain(int wait) throws InterruptedException {
        wait(wait);
    }

    @Then("I verify the task was set up")
    public void verifyTaskIsSetUp() {
        assertEquals(1, toffeeContext.getTotalCorePoolSize());
    }

    @Then("I verify {int} tasks were set up")
    public void verifyNumerousTasksAreSetUp(int count) {
        assertEquals(count, toffeeContext.getTotalCorePoolSize());
    }

    @And("I verify the task has run {int} times in total")
    public void verifyTaskHasRun(int noOfInvocations) {
        assertEquals(noOfInvocations, atomicInteger.get());
    }

    @And("I shutdown all tasks now")
    public void shutDownAllTasksNow() {
        toffeeContext.shutDownAllTasksNow();
    }
}
