package com.norbertgogiel.toffee;

public class IntervalScheduledTaskAgentProvider {

    public IntervalScheduledTaskAgent get() {
        return new IntervalScheduledTaskAgent(1);
    }
}
