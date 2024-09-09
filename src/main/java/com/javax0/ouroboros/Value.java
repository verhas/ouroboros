package com.javax0.ouroboros;

/**
 * The result of an evaluation of a command or block.
 */
public interface Value<T> {
    T get();
    default void set(T t){
        throw new UnsupportedOperationException("Setting the value is not supported");
    }
    Value<T> copy();
}
