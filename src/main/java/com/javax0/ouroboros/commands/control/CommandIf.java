package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_if
 * {%COMMAND if%}
 * <p>
 * If the first argument is `true`, the second argument is executed, otherwise the third one.
 * <p>
 * The command returns the value of the executed block.
 * <p>
 * The third argument may be missing or be `{}`.
 * Note that it can only be missing if the `if` command is the last command of the program or in a block.
 * <p>
 * {%EXAMPLE/if%}
 * end
 *
 * @param <T>
 */
public class CommandIf<T> extends AbstractCommand<T> {

    public CommandIf(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var condition = nextArgument(context, this::toBoolean).orElse(false);
        final var then = interpreter.pop();
        final var otherwise = interpreter.pop();
        final var block = condition ? then : otherwise;
        if (block == null) {
            return null;
        }
        return interpreter.evaluate(context, block);
    }

}
