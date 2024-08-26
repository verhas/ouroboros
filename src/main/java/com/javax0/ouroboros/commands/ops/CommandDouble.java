package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.List;

/**
 * Command to print the value of the top of the stack.
 */
@AbstractCommand.Arguments(1)
public class CommandDouble extends AbstractCommandOp<Double> {

    public CommandDouble(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Double> execute(Context context, List<Block> arguments) {
        final var value = interpreter.evaluate(context, arguments.getFirst());
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
