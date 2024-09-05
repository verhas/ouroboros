package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_double
 * {%COMMAND double%}
 * Create a double value.
 * The argument the command uses is the value and converts it to double.
 * The argument can be integer, float, boolean, string, or even BigInteger and BigDecimal.
 * <p>
 * end
 */
public class CommandDouble extends AbstractCommand<Double> {

    public CommandDouble(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Double> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toDouble(o))).orElse(null);
    }


}
