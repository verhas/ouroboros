package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_first
 * {%COMMAND first, car%}
 * <p>
 * Fetch and return the first element of a list.
 * The argument is the list.
 * The command is also registered as `car` to follow the LISP tradition.
 * <p>
 * {%EXAMPLE/list_first%}
 * <p>
 * end
 *
 * @param <T>
 */

public class CommandListFirst<T> extends AbstractCommand<T> {
    public CommandListFirst(Interpreter interpreter) {
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
                return (Value<T>) lv.values().get(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
