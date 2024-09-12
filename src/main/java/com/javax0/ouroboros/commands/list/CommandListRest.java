package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_rest
 * {%COMMAND rest, cdr%}
 * This command returns a new list that contains all the elements of the list except the first one.
 * If the list is empty, then the command throws an exception.
 * The argument is the list.
 *
 * The command is also registered as `cdr` to follow the LISP tradition.
 * <p>
 * {%EXAMPLE/list_rest%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandListRest<T> extends AbstractCommand<ListValue<T>> {
    public CommandListRest(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<ListValue<T>> execute(Context context) {
        final var list = nextArgument(context)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'rest"));
        switch (list) {
            case ListValue<?> lv -> {
                if (lv.values().isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                final var newList = new ListValue<T>(interpreter);
                for (int i = 1; i < lv.values().size(); i++) {
                    newList.values().add((Value<T>) lv.values().get(i));
                }
                return new SimpleValue<>(newList);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
