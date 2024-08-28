package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.Objects;

/**
 * Command to print the value of the top of the stack.
 */

public class CommandPuts extends AbstractCommand<String> {

    public CommandPuts(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        final var value = nextArgument(context).orElse(null);
        //final var value = interpreter.evaluate(context, interpreter.pop());
        String result = "" + (value == null ? null : value);
        interpreter.output(result);
        return new SimpleValue<>(result);
    }

    @Override
    public String toString() {
        return "CommandPuts";
    }
}
