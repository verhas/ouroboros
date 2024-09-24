package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_last
 * {%COMMAND last%}
 * <p>
 * The command returns the last element of the list.
 * The argument is the list.
 * <p>
 * {%EXAMPLE/list_last%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandListLast<T> extends AbstractCommand<T> {
    public CommandListLast(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var list = nextArgument(context)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'first"));
        switch (list) {
            case ListValue<?> lv -> {
                if (lv.values().isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                return (Value<T>) lv.values().get(lv.values().size() - 1);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
