package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**command_quote
 * {%COMMAND quote%}
 *
 * Return the argument as a value without evaluating it.
 * The command can be used as `quote` but it is also abbreviated as `'`.
 * end
 *
 * @param <T> the type of the value
 */
public class CommandQuote<T> extends AbstractCommand<T> {

    public CommandQuote(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var arg = interpreter.pop();
        return new SimpleValue<>((T) arg);
    }
}
