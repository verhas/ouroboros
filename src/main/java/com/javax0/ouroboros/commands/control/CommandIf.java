package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * snippet command_if
 * {%COMMAND if%}
 * <p>
 * If the first argument is true then the second argument is executed, otherwise the third argument is executed.
 * <p>
 * The command returns the value of the executed block.
 * <p>
 * The third argument may be missing, or be `{}`.
 * Note that it can only be missing if the `if` command is the last command of the program or a block.
 * <p>
 * end snippet
 *
 * @param <T>
 */
public class CommandIf<T> extends AbstractCommand<T> {

    public CommandIf(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var condition = nextArgument(context, this::toBoolean)
                .orElseThrow(() -> new IllegalArgumentException("Condition is missing"));
        final var then = interpreter.pop();
        final var otherwise = interpreter.pop();
        final var block = condition ? then : otherwise;
        if (block == null) {
            return null;
        }
        return interpreter.evaluate(context, block);
    }

}
