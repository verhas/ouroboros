package com.javax0.ouroboros;

public class SimpleValue<T> implements Value<T>{
    private final T value;

    public SimpleValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }
}
