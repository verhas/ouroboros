package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_setl
 * {%COMMAND setl%}
 * Set the element of a list at some position.
 * The position is zero-based.
 * The first argument is the list.
 * The second argument is the index.
 * The third argument is the value.
 * <p>
 * {%EXAMPLE/list_set%}
 * end
 *
 * @param <T>
 */
public class CommandListSet<T> extends AbstractCommand<T> {
    public CommandListSet(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var list = nextArgument(context)
                .orElseThrow(() -> new IllegalArgumentException("There is bad second argument to the command 'set'"));
        final var index = nextArgument(context, this::toLong).map(Math::toIntExact)
                .orElseThrow(() -> new IllegalArgumentException("The index is missing"));
        final var value = nextArgument(context)
                .map(SimpleValue::new)
                .map(Value.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("The value is missing"));
        switch (list) {
            case ListValue<?> lv -> {
                if (lv.isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                return (Value<T>) lv.set(index, value);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
