package com.vangogiel.toffee;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class ScheduledTaskRunnable implements Runnable {

  private final Class<?> source;
  private final Method method;

  public ScheduledTaskRunnable(Class<?> source, Method method) {
    this.source = source;
    this.method = method;
  }

  @Override
  public void run() {
    try {
      method.invoke(source.getDeclaredConstructor().newInstance());
    } catch (IllegalAccessException
        | InstantiationException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new UndeclaredThrowableException(e);
    }
  }
}
