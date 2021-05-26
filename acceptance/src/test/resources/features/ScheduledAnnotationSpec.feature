Feature: Interval Scheduled Tasks

  Scenario: Task runs all the time every second
    Given a task that is set to run all the time every second
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 4 times in total

  Scenario: Task runs all the time every 3 seconds
    Given a task that is set to run all the time every 3 seconds
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 2 times in total

  Scenario: Task is set to run in the future before midnight
    Given a task that is set to run in the future before midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 0 times in total

  Scenario: Task is set to run in the future after midnight
    Given a task that is set to run in the future after midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 0 times in total

  Scenario: Task is set to run in the future over midnight
    Given a task that is set to run in the future over midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 0 times in total

  Scenario: Two tasks run all the time at different rates
    Given two tasks scheduled to run all the time at different rates
    When I verify 2 tasks were set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 6 times in total

  Scenario: Task is set to run and all tasks are shut down
    Given a task that is set to run all the time every second
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the task has run 4 times in total
    Then again I wait 3 seconds
    And I verify the task has run 4 times in total
