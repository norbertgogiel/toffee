package io.vangogiel.toffee;

/**
 * Annotation processing interface defining a class's structure to integrate with the application.
 *
 * <p>A class implementing this interface defines a logic to process information from an annotation
 * or set of annotations depending on the logic.
 *
 * <p>The interface is to be viewed as a processing step between raw format from annotations and the
 * format type it is converted to before use.
 *
 * <p>Two objects have to be defined upon implementation of this interface. The first object {@code
 * <S>} is the raw information from the annotation. This could be any form that represents a single
 * logic to process. The second object {@code <T>} is the target object into which the data will be
 * wrapped in.
 *
 * @param <S> source class of the annotation
 * @param <T> target class
 */
public interface AnnotationProcessor<S, T> {

  /**
   * Processes a source of the information and converts it into more understandable and manageable
   * format and returns it.
   *
   * @param source representing the origin of the information to be processed
   * @return converted information
   */
  T process(S source);
}
