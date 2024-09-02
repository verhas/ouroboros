package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.utils.SafeCast;

/**
 * command_list_set
 * {%COMMAND list.set%}
 * A list method that sets an element of a list at a given position.
 * The position is zero-based.
 * end
 * @param <T>
 */
public class CommandListSet<T> extends AbstractCommand<T> {
    public CommandListSet(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var index = nextArgument(context, this::toLong).map(Math::toIntExact)
                .orElseThrow(() -> new IllegalArgumentException("The index is missing"));
        final var value = nextArgument(context)
                .map(SimpleValue::new)
                .map(Value.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("The value is missing"));
        final var list = context.variable("this")
                .map(Value::get)
                .map(SafeCast.to(ListValue.class))
                .map(ListValue::values)
                .orElseThrow(() -> new IllegalArgumentException("There is bad second argument to the command 'set'"));
                if (list.isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                return (Value<T>) list.set(index,value);
    }
}
