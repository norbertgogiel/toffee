package com.norbertgogiel.toffee;

public class ToffeeApplication {

    public void init(Class<?> source) {
        assertNotNull(source);
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }
}
