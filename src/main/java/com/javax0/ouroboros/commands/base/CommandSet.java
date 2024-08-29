package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * Set a variable in the context.
 */
public class CommandSet extends AbstractCommand<Void> {
    public CommandSet(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final String name = getName(context)
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'set' should be a name"));
        final var value = interpreter.evaluate(context, interpreter.pop());
        context.set(name, value);
        return null;
    }


}
