package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_long
 * {%COMMAND long%}
 * Create a long value.
 * The argument the command uses is the value and converts it to long.
 * The argument can be integer, float, boolean, string, or even BigInteger and BigDecimal.
 * <p>
 * end
 */

public class CommandLong extends AbstractCommand<Long> {

    public CommandLong(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public Value<Long> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toLong(o))).orElse(null);
    }


}
