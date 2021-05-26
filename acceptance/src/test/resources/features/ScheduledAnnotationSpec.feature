Feature: Interval Scheduled Tasks

  Scenario: Task runs all the time every second
    Given a task that is set to run all the time every second
    When I verify the task was set up
    Then I wait 3 seconds
    And I verify the task has run 4 times
  
  Scenario: Task is set to run in the future before midnight
    Given a task that is set to run in the future
    When I verify the task was set up
    Then I wait 3 seconds
    And I verify the task has run 0 times