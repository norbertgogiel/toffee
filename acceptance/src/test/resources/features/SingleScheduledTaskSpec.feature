Feature: Scheduled single tasks

  Scenario: Task runs all the time every second
    Given a task set to run non-stop with every second custom annotation
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 4 times in total

  Scenario: Task runs all the time every 3 seconds
    Given a task set to run non-stop with every 3-seconds annotation
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 2 times in total

  Scenario: Task is set to run in the future before midnight
    Given a task that is set to run in the future before midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 0 times in total

  Scenario: Task is set to run in the future after midnight
    Given a task that is set to run in the future after midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 0 times in total

  Scenario: Task is set to run in the future over midnight
    Given a task that is set to run in the future over midnight
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 0 times in total

  Scenario: Task is scheduled to run and all tasks are shut down correctly
    Given a task set to run non-stop with every second annotation
    When I verify the task was set up
    Then I wait 3.5 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 4 times in total
    Then again I wait 3 seconds
    And I verify the tasks have run 4 times in total

  Scenario: Task is scheduled using every second period annotation
    Given a task set to run non-stop with every second annotation
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 4 times in total

  Scenario: Task is scheduled using every minute period annotation
    Given a task set to run non-stop with every minute annotation
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 1 times in total

  Scenario: Task is scheduled using every hour annotation
    Given a task set to run non-stop with every hour annotation
    When I verify the task was set up
    Then I wait 3 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 1 times in total
