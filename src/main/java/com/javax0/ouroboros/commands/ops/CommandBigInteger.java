package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.math.BigInteger;

/**
 * command_biginteger
 * {%COMMAND BigInteger%}
 * Create a big decimal value.
 * The argument the command uses is the value and converts it to big integer.
 * The argument can be integer, float, boolean, string, or even BigInteger and BigDecimal.
 * <p>
 * The argument is usually a string.
 * <p>
 * end
 */
public class CommandBigInteger extends AbstractCommand<BigInteger> {

    public CommandBigInteger(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<BigInteger> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toBigInteger(o))).orElse(null);
    }


}
