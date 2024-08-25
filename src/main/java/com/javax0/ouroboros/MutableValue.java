package com.javax0.ouroboros;

public class MutableValue<T> implements Value<T>{
    private T value;

    public MutableValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
