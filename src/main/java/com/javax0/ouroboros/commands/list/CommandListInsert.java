package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.utils.SafeCast;

/**
 * command_insert
 * {%COMMAND insert%}
 * The command inserts an element into a list at a given position.
 * The position is zero-based.
 * <p>
 * The first argument is the list.
 * The second argument is the index.
 * The third argument is the value.
 * <p>
 * {%EXAMPLE/list_insert%}
 * end
 *
 * @param <T>
 */
public class CommandListInsert<T> extends AbstractCommand<T> {
    public CommandListInsert(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var list = nextArgument(context)
                .filter(it -> it instanceof ListValue)
                .map(SafeCast.to(ListValue.class))
                .map(ListValue::values)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'insert"));
        final var index = nextArgument(context, this::toLong).map(Math::toIntExact)
                .orElseThrow(() -> new IllegalArgumentException("The index is missing"));
        final var value = nextArgument(context)
                .map(SimpleValue::new)
                .map(it -> (Value<T>) it)
                .orElseThrow(() -> new IllegalArgumentException("The value is missing"));
        if (list.isEmpty()) {
            if (index == 0) {
                list.add(value);
                return value;
            }
            throw new IllegalArgumentException("The list is empty");
        }
        Object v = value;
        if (index < list.size()) {
            for (int i = index; i < list.size(); i++) {
                final var moveValue = list.get(i);
                list.set(i, v);
                if (i == list.size() - 1) {
                    list.add(moveValue);
                    break;
                }
                v = moveValue;
            }
        } else {
            list.add(value);
        }
        return value;
    }
}

