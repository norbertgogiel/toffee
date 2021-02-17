package com.norbertgogiel.toffee;

public class ToffeeApplication {

    public void init(Class<?> source) {
        if (source == null) {
            throw new IllegalArgumentException("source object must not be null");
        }
    }
}
