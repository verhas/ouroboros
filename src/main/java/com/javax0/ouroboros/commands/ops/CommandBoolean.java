package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * Command to print the value of the top of the stack.
 */

public class CommandBoolean extends AbstractCommand<Boolean> {

    public CommandBoolean(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Boolean> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toBoolean(o))).orElse(null);
    }


}
