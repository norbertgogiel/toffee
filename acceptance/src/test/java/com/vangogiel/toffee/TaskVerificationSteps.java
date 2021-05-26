package com.vangogiel.toffee;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class TaskVerificationSteps {

    @When("I wait {double} seconds")
    public void wait(double wait) throws InterruptedException {
        Thread.sleep((long)(wait * 1000L));
    }

    @When("again I wait {double} seconds")
    public void waitAgain(double wait) throws InterruptedException {
        wait(wait);
    }

    @Then("I verify the task was set up")
    public void verifyTaskIsSetUp() {
        assertEquals(1, ContextSetupSteps.toffeeContext.getTotalCorePoolSize());
    }

    @Then("I verify {int} tasks were set up")
    public void verifyNumerousTasksAreSetUp(int count) {
        assertEquals(count, ContextSetupSteps.toffeeContext.getTotalCorePoolSize());
    }

    @And("I verify the tasks have run {int} times in total")
    public void verifyTaskHasRun(int noOfInvocations) {
        assertEquals(noOfInvocations, ContextSetupSteps.atomicInteger.get());
    }

    @And("I shutdown all tasks now")
    public void shutDownAllTasksNow() {
        ContextSetupSteps.toffeeContext.shutDownAllTasksNow();
    }
}
