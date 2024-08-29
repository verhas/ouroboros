package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * Set a global variable in the context.
 */
public class CommandSetg extends AbstractCommand<Void> {
    public CommandSetg(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final String name = getName(context)
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'setg' should be a name"));
        final var value = interpreter.evaluate(context, interpreter.pop());
        context.setg(name, value);
        return null;
    }


}
