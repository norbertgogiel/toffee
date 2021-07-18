package io.vangogiel.toffee;

/**
 * Provider class instantiating a dependency upon method invocation.
 *
 * <p>Responsible for {@link IntervalScheduledTaskAgent} instantiation.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see IntervalScheduledTaskProcessor
 * @see ToffeeContext
 */
public class IntervalScheduledTaskAgentProvider {

  /**
   * Instantiates a new {@code IntervalScheduledTaskAgent} and returns the reference to the caller.
   *
   * @return a new instance of {@link IntervalScheduledTaskAgent}.
   */
  public IntervalScheduledTaskAgent get() {
    return new IntervalScheduledTaskAgent(1);
  }
}
