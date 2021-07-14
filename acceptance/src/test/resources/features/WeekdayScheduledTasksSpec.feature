Feature: Weekday Scheduled Tasks
  
  Scenario: Task is set to run all the time on Monday
    Given I set today to be Tuesday
    And a task scheduled to run on Mondays
    Then I wait 3.2 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 0 times in total

  Scenario: Task is set to run all the time on Tuesday
    Given I set today to be Tuesday
    And a task scheduled to run on Tuesdays
    When I verify 1 tasks were set up
    Then I wait 3.2 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 4 times in total

  Scenario: Task is set to run all the time on Mon, Wed, Fri
    Given I set today to be Tuesday
    And a task scheduled to run on Mon, Wed, Fri
    When I verify 0 tasks were set up
    Then I wait 3.2 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 0 times in total

  Scenario: Task is set to run all the time on Tue, Wed, Fri
    Given I set today to be Tuesday
    And a task scheduled to run on Tue, Wed, Fri
    When I verify 1 tasks were set up
    Then I wait 3.2 seconds
    And I shutdown all tasks now
    And I verify the tasks have run 4 times in total
