package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_shift
 * {%COMMAND shift%}
 * <p>
 * Get the argument following the block with evaluation.
 * <p>
 * The pair of the commands is `arg` that fetched the next argument without evaluation.
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandShift<T> extends AbstractCommand<T> {

    public CommandShift(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var savedBlocks = interpreter.up();
        try {
            return new SimpleValue<>(this.<T>nextArgument(context).orElse(null));
        } finally {
            interpreter.down(savedBlocks);
        }
    }


}
