package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_at
 * {%COMMAND at%}
 * Get the element at the given index.
 * The position is zero-based.
 * The argument is either a list or a string.
 * <p>
 * {%EXAMPLE/list_get%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandAt<T> extends AbstractCommand<T> {
    public CommandAt(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var list = nextArgument(context)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'first"));
        final var index = nextArgument(context, this::toLong).map(Math::toIntExact)
                .orElseThrow(() -> new IllegalArgumentException("The index is missing"));
        switch (list) {
            case ListValue<?> lv -> {
                if (lv.values().isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                return (Value<T>) lv.values().get(index);
            }
            case String s -> {
                if (s.length() <= index) {
                    throw new IllegalArgumentException("The string is shorter than the index");
                }
                return Value.<T>of((T)s.substring(Math.toIntExact(index),Math.toIntExact(index+1)));
            }
            case Source s -> {
                if (s.toString().length() <= index) {
                    throw new IllegalArgumentException("The source is shorter than the index");
                }
                return Value.<T>of((T)s.toString().substring(Math.toIntExact(index),Math.toIntExact(index+1)));
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
