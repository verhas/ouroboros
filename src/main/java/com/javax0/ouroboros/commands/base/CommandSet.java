package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_set
 * {%COMMAND set%}
 * Set a variable in the context.
 * <p>
 * The first argument is the name of the variable, and the second argument is the value.
 * <p>
 * {%EXAMPLE/set1%}
 * <p>
 * {%EXPLANATION/set1_explanation%}
 * end
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
