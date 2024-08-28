package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.math.BigDecimal;

/**
 * Command to print the value of the top of the stack.
 */

public class CommandBigDecimal extends AbstractCommand<BigDecimal> {

    public CommandBigDecimal(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<BigDecimal> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toBigDecimal(o))).orElse(null);
    }

    @Override
    public String toString() {
        return "CommandBigDecimal";
    }
}
