package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_error
 * {%COMMAND error%}
 * Stop the execution with an error.
 * The argument of the command is the error message.
 * <p>
 * end
 */
public class CommandError extends AbstractCommand<Void> {

    public CommandError(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final var value = nextArgument(context).orElse(null);
        String result = "" + value;
        throw new RuntimeException(result);
    }


}
