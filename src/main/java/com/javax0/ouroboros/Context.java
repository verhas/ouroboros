package com.javax0.ouroboros;

import java.util.Optional;

/**
 * An execution context.
 */
public interface Context {
    /**
     * Get the value of the variable with the given name.
     *
     * @param name the name of the variable
     * @param <T> the type of the value
     * @return the value of the variable
     */
    <T> Optional<Value<T>> variable(String name);

    /**
     * Remove the variable with the given name.
     *
     * @param name the name of the variable
     */
    void remove(String name);

    /**
     * Set the value of the variable with the given name.
     *
     * @param name the name of the variable
     * @param <T> the type of the value
     * @param value the value of the variable
     */
    <T> void set(String name, Value<T> value);

    /**
     * Set the value of the global variable with the given name.
     * @param name the name of the variable
     * @param value the value of the variable
     * @param <T> the type of the value
     */
    <T> void setg(String name, Value<T> value);
}
