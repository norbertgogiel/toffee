Feature: Scheduled multiple tasks

  Scenario: Two tasks run all the time at different rates
    Given two tasks scheduled to run all the time at different rates
    When I verify 2 tasks were set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 6 times in total
