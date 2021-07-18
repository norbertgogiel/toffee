package io.vangogiel.toffee.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify an hourly period of running for a scheduled method.
 *
 * <p>This is static descriptor for {@link io.vangogiel.toffee.TimePeriodAnnotationProcessor} to
 * recognise as an hourly run for a scheduled task.
 *
 * @author Norbert Gogiel
 * @since 1.0
 * @see io.vangogiel.toffee.TimePeriodAnnotationProcessor
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EveryHour {}
