package com.norbertgogiel.toffee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

public class TestIntervalScheduledTaskAgent {

    @Test
    public void createInstanceOfIntervalScheduledTaskAgent() {
        assertDoesNotThrow(() -> new IntervalScheduledTaskAgent(1));
    }
}
