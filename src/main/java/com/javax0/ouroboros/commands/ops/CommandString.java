package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.List;

/**
 * Command to print the value of the top of the stack.
 */
@AbstractCommand.Arguments(1)
public class CommandString extends AbstractCommandOp<String> {

    public CommandString(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<String> execute(Context context, List<Block> arguments) {
        final var value = interpreter.evaluate(context, arguments.getFirst());
        if (value == null) {
            return null;
        }
        return new SimpleValue<>(toString(value.get()));
    }

    @Override
    public String toString() {
        return "CommandLong";
    }
}
