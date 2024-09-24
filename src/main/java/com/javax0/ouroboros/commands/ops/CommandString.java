package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_string
 * {%COMMAND string%}
 * Convert the argument to string.
 * The command converts the argument to string.
 * end
 */
public class CommandString extends AbstractCommand<String> {

    public CommandString(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toString(o))).orElse(null);
    }


}
