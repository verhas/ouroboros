package com.javax0.ouroboros;

import java.util.Optional;

/**
 * An execution context.
 * <p>
 * An execution context is a set of variables that are available during the execution of the program.
 * A variable is a {@code (name, value)} pair.
 * There is no restriction in the context definition what the name can be other than it is a String.
 * <p>
 * The context provides basic CRUD operations for the variables.
 * <p>
 * Variables are also stored in a hierarchy in the context.
 * The top of the hierarchy is the global namespace.
 * At every level of the hierarchy the variables are stored in an object.
 * An object is defined in {@link ObjectValue}.
 */
public interface Context {
    /**
     * Get the value of the variable with the given name.
     *
     * @param name the name of the variable
     * @param <T>  the type of the value
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
     * @param name  the name of the variable
     * @param <T>   the type of the value
     * @param value the value of the variable
     */
    <T> void set(String name, Value<T> value);

    /**
     * Set the value of the global variable with the given name.
     *
     * @param name  the name of the variable
     * @param value the value of the variable
     * @param <T>   the type of the value
     */
    <T> void setg(String name, Value<T> value);

    ObjectValue up();

    ObjectValue down();

    void down(ObjectValue variables);

    /**
     * Get the most local variables as an object.
     *
     * @return the object that contains all the local variables
     */
    ObjectValue bottom();

    /**
     * Get the closure of the context.
     *
     * The closure of the context is the object that contains all the variables in the context.
     * @return the closure of the context
     */
    ObjectValue closure();
}
