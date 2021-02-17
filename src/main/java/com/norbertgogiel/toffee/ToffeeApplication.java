package com.norbertgogiel.toffee;

import com.norbertgogiel.toffee.annotations.IntervalScheduled;
import com.norbertgogiel.toffee.annotations.ScheduledFrom;
import com.norbertgogiel.toffee.annotations.ScheduledUntil;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ToffeeApplication {

    public void init(Class<?> source) {
        assertNotNull(source);
        if (source.isAnnotationPresent(IntervalScheduled.class)) {
            Arrays.stream(source.getMethods())
                    .forEach(method -> {
                        if (method.isAnnotationPresent(ScheduledFrom.class)
                        && method.isAnnotationPresent(ScheduledUntil.class))
                                processScheduled(method);
            });
        }
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    private void processScheduled(Method method) {
        throw new UnsupportedOperationException();
    }
}
