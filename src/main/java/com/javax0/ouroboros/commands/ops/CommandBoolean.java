package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * Command to print the value of the top of the stack.
 */

/**
 * command_boolean
 * {%COMMAND boolean%}
 * Create a boolean value.
 * <p>
 * The argument the command uses is the value and converts it to boolean.
 * The argument can be an integer, a floating point number, BigInteger, BigDecimal, string, boolean, or null.
 * end
 */
public class CommandBoolean extends AbstractCommand<Boolean> {

    public CommandBoolean(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<Boolean> execute(Context context) {
        return nextArgument(context).map(o -> new SimpleValue<>(toBoolean(o))).orElse(null);
    }


}
