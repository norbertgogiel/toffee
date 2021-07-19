# Toffee

[![CI](https://github.com/vangogiel/toffee/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/vangogiel/toffee/actions/workflows/build.yml)
[![License](http://img.shields.io/badge/License-Apache%202.0-brightgreen.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](http://maven-badges.herokuapp.com/maven-central/io.vangogiel.toffee/toffee/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.vangogiel.toffee/toffee)


Systems running backend application need to be able to run multiple parallel tasks without much 
complexity to set them up. Very often tasks need to be run at certain periods between task execution.
Not only that, very often tasks are required to be scheduled to be run only between certain hours of the day
on certain days of week.

Toffee is an open source project to implement versatile annotations-only style of scheduling tasks. 
It allows scheduling tasks by annotating a method or multiple methods. 
Tasks can be scheduled for chosen set of days of week and between specific time set by the user. 
It is built for Java and is compliant with JDK 9+.

## Documentation     

In order to run the application, the user has to instantiate Toffee context with the source class 
or classes (with an example of a class shown below), such that

```java
new ToffeeContext(MyExampleClass.class);
```
or
```java
new ToffeeContext(
         MyExampleClass.class,
         MySecondExampleClass.class
);
```

The ```ToffeeContext.class``` offers API to query basic state of tasks for the day, which 
is useful for logging.

The simplest way to schedule a task set to run all day and every day is the following:
     
```java
public class MyExampleClass {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "23:59:59")
    public void myTask() {
        ...
    }
}
```

By default, task annotated as above will run every second. A task can also be scheduled to be run at custom periods. 
For example, if the task above is required to be run every 20 minutes then it can be annotated as following:

```java
public class MyExampleClass {

    @ScheduledFrom(time = "00:00:00")
    @ScheduledUntil(time = "23:59:59")
    @Every(period = 20, timeUnit = TimeUnit.MINUTES)
    public void myTask() {
        ...
    }
}
```

There is range of time units that are accepted and processed. Those are:

```java
TimeUnit.HOURS
TimeUnit.MINUTES
TimeUnit.SECONDS
```

Every single number provided will be used to convert into seconds from the time unit provided by the user.
For simplicity there are three more annotations to specify:

- a run every single hour
```java
@EveryHour
```
- a run every single minute
```java
@EveryMinute
```
- a run every single second
```java
@EverySecond
```
 
Alternatively, if the task has to be only run between certain ours of the day, the annotations in
the above example would be set as follows:

```java
@ScheduledFrom(time = "09:00:00")
@ScheduledUntil(time = "17:00:00")
```

Tasks can also be scheduled for certain days of week, not just to run every day. 
Annotation ```@Weekdays``` allows specifying days of week on which the task should run. 
The user can specify multiple days by separating them with a comma. Any other delimiter 
will be ignored which in effect will ignore the list of days. An example of correctly
scheduling a task for multiple weekdays can be shown below:

```java
public class MyExampleClass {

    @ScheduledFrom(time = "09:00:00")
    @ScheduledUntil(time = "17:00:00")
    @EverySecond
    @Weekdays(days = "Mon,Wed,Fri")
    public void myTask() {
        ...
    }
}
```

Reporting Issues
----------------
Toffee is open source software and uses Github's integrated issue tracking system to record bugs 
and feature requests. If you want to raise an issue, please raise it in [Issue Tracker](https://github.com/vangogiel/toffee/issues). 
Please provide as much information as you can and provide a test case in order to replicate the issue.     

License
-------
Toffee is open source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).