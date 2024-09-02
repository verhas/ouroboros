package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * snippet command_arg
 * {%COMMAND arg%}
 * Get the argument following the block without evaluation.
 * <p>
 * This command is similar to the command `shift`, but it does not evaluate the argument.
 * It has the similar effect as quoting the argument and then calling shift.
 * <p>
 * This command can be used to create commands (functions, methods) that evaluate some of the arguments conditionally.
 * <p>
 * end snippet
 *
 * @param <T>
 */
public class CommandArg<T> extends AbstractCommand<T> {

    public CommandArg(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var savedBlocks = interpreter.up();
        try {
            final var object = interpreter.pop();
            return new SimpleValue<>((T) object);
        } finally {
            interpreter.down(savedBlocks);
        }
    }
}
