package io.vangogiel.toffee;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * Steps allowing to verify task runs per context setup.
 */
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
        waitAssertNumerousTasksSetUp(1);}

    @Then("I verify {int} tasks were set up")
    public void verifyNumerousTasksAreSetUp(int expected) {
        waitAssertNumerousTasksSetUp(expected);
    }

    @And("I verify the tasks have run {int} times in total")
    public void verifyTaskHasRun(int noOfInvocations) {
        assertEquals(noOfInvocations, ContextSetupSteps.atomicInteger.get());
    }

    @And("I shutdown all tasks now")
    public void shutDownAllTasksNow() {
        ContextSetupSteps.toffeeContext.shutDownAllTasksNow();
    }

    private void waitAssertNumerousTasksSetUp(int expected) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 2000) {
            if (expected == ContextSetupSteps.toffeeContext.getTotalCorePoolSize()) {
                return;
            }
        }
        throw new AssertionError("Timed out wait assertion. Expected: " + expected + " actual: " + ContextSetupSteps.toffeeContext.getTotalCorePoolSize());
    }
}
