Feature: Scheduled multiple tasks

  Scenario: Two tasks run all the time at different rates
    Given two tasks scheduled to run all the time at different rates
    When I verify 2 tasks were set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 6 times in total

  Scenario: Five tasks have run at different times and different rates, every 3 seconds, second, minute, hour
    Given five tasks scheduled to run at different times and at different rates
    When I verify 5 tasks were set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 8 times in total