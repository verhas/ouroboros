package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_bw
 * {%COMMAND bw%}
 * Create a bare word from a string.
 * <p>
 * end
 */
public class CommandBW<T> extends AbstractCommand<T> {

    public CommandBW(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var value = nextArgument(context).orElse(null);
        String result = "" + value;
        return new BareWord<T>(interpreter, result).execute(context);
    }


}
