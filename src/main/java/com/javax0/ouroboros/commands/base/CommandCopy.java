package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.utils.SafeCast;

/**
 * command_copy
 * {%COMMAND copy%}
 * <p>
 * Create a deep copy of and object.
 * <p>
 * {%EXAMPLE/copy%}
 * {%EXPLANATION/copy_explanation%}
 * end
 *
 * @param <T> the type of the value
 */

public class CommandCopy<T> extends AbstractCommand<T> {
    public CommandCopy(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        return nextArgument(context)
                .map(SimpleValue::new)
                .map(SafeCast.to(Value.class))
                .map(Value::copy)
                .orElseThrow(() -> new IllegalArgumentException("The value is missing"));
    }
}