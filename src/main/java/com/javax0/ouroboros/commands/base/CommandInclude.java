package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_include
 * {%COMMAND include%}
 * Include a file in the input.
 * <p>
 * end
 */
public class CommandInclude extends AbstractCommand<Void> {

    public CommandInclude(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final var value = nextArgument(context).orElse(null);
        String fn = "" + value;
        interpreter.include(fn);
        return null;
    }


}
