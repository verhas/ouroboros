package com.javax0.ouroboros;

public class SimpleValue<T> implements Value<T> {
    private T value;

    public SimpleValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public Value<T> copy() {
        return
                switch (value) {
                    case Value<?> v -> new SimpleValue<>((T) v.copy());
                    default -> new SimpleValue<>(value);
                };
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
