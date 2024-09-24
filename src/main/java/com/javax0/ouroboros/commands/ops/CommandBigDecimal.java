package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.math.BigDecimal;

/**
 * command_bigdecimal
 * {%COMMAND BigDecimal%}
 * Create a big decimal value.
 * The argument the command uses is the value and converts it to big decimal.
 * The argument can be integer, float, boolean, string, or even BigInteger and BigDecimal.
 * <p>
 * The argument is usually a string.
 * <p>
 * end
 */

public class CommandBigDecimal extends AbstractCommand<BigDecimal> {

    public CommandBigDecimal(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<BigDecimal> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toBigDecimal(o))).orElse(null);
    }


}
