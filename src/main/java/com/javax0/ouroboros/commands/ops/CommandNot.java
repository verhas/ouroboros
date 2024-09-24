package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_not
 * {%COMMAND not%}
 * Negate the value.
 * The command negates the value. If the value is true then the result is false and if the value is false then the result is true.
 * end
 */
public class CommandNot extends AbstractCommand<Boolean> {

    public CommandNot(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<Boolean> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(!toBoolean(o))).orElse(null);
    }


}
