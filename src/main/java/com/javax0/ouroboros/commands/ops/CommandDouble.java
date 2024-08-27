package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommandOp;

/**
 * Command to print the value of the top of the stack.
 */

public class CommandDouble extends AbstractCommandOp<Double> {

    public CommandDouble(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Double> execute(Context context) {
        final var value = interpreter.evaluate(context, interpreter.pop());
        if (value == null) {
            return null;
        }
        return new SimpleValue<>(toDouble(value.get()));
    }

    @Override
    public String toString() {
        return "CommandDouble";
    }
}
