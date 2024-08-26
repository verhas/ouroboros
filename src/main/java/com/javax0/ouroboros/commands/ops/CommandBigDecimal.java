package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Command to print the value of the top of the stack.
 */
@AbstractCommand.Arguments(1)
public class CommandBigDecimal extends AbstractCommandOp<BigDecimal> {

    public CommandBigDecimal(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<BigDecimal> execute(Context context, List<Block> arguments) {
        final var value = interpreter.evaluate(context, arguments.getFirst());
        if (value == null) {
            return null;
        }
        return new SimpleValue<>(toBigDecimal(value.get()));
    }

    @Override
    public String toString() {
        return "CommandBigDecimal";
    }
}
