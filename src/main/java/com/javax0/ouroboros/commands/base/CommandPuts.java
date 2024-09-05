package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_puts
 * {%COMMAND puts%}
 * Print the value as a string.
 * <p>
 * {%EXAMPLE/puts%}
 * end
 */
public class CommandPuts extends AbstractCommand<String> {

    public CommandPuts(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        final var value = nextArgument(context).orElse(null);
        String result = "" + value;
        interpreter.output(result);
        return new SimpleValue<>(result);
    }


}
