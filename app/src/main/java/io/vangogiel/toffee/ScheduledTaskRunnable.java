package io.vangogiel.toffee;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Encapsulates a {@link Runnable} as a scheduled no-args method.
 *
 * <p>The class implements a {@link Runnable} and implements its default method.
 *
 * <p>Exceptions related to invocation, instantiation and related to generics are propagates to the
 * caller as {@link UndeclaredThrowableException}.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 */
public class ScheduledTaskRunnable implements Runnable {

  private final Class<?> source;
  private final Method method;

  /**
   * Create a new ScheduledTaskRunnable.
   *
   * @param source the source class of the method
   * @param method the method in subject
   */
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
