package com.javax0.ouroboros;

import java.util.List;

/**
 * An instance of a class implementing this interface executes a specific command.
 * <p>
 * A very simple example is retrieving a constant. The execution of the command will simply return the constant.
 */
public interface Command<T> extends Block {

    void set(Interpreter interpreter);

    /**
     * Execute the command.
     *
     * @return the value that the command returns
     */
    Value<T> execute(Context context);

    default List<Block> subBlocks() {
        throw new IllegalArgumentException("Commands do not have sub blocks");
    }

}


