package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_list_last
 * {%COMMAND list.last%}
 * <p>
 * A method defined on every list object that returns the last element of the list.
 * If the list is empty, then the command throws an exception.
 * The command is defined on the list object.
 * The command does not have any argument.
 * The command returns the last element of the list.
 * <p>
 * {%EXAMPLE/list_last%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandListLast<T> extends AbstractCommand<T> {
    public CommandListLast(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var list = context.variable("this").map(Value::get)
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
