package com.javax0.ouroboros;

/**
 * The result of an evaluation of a command or block.
 */
public interface Value<T> {
    T get();
}
