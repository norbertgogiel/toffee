package com.vangogiel.toffee;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class ScheduledAnnotationSpecSteps {

    private ToffeeContext toffeeContext;

    static final AtomicInteger atomicInteger = new AtomicInteger();

    @Given("a task that is set to run all the time every second")
    public void basicContextWithContinuousTask() {
       atomicInteger.set(0);
       toffeeContext = new ToffeeContext(IntervalScheduledToRunAllTheTime.class);
    }

    @Given("a task that is set to run in the future")
    public void basicContextFutureTask() {
        atomicInteger.set(0);
        toffeeContext = new ToffeeContext(IntervalScheduledToRunInTheFuture.class);
    }

    @When("I wait {int} seconds")
    public void wait(int wait) throws InterruptedException {
        Thread.sleep(wait * 1000L);
    }

    @Then("I verify the task was set up")
    public void verifyTaskIsSetUp() {
        assertEquals(1, toffeeContext.getTotalCorePoolSize());
    }

    @And("I verify the task has run {int} times")
    public void verifyTaskHasRun(int noOfInvocations) {
        toffeeContext.shutDownAllTasksNow();
        assertEquals(noOfInvocations, atomicInteger.get());
    }
}
