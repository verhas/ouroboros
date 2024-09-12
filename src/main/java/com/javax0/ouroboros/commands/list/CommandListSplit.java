package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_split
 * {%COMMAND split%}
 * This command splits a list into two lists at a given index.
 * The index is zero-based.
 * The method returns a list of two lists.
 * The first list contains the elements from the beginning of the list up to the index.
 * The second list contains the elements from the index to the end of the list.
 * <p>
 * {%EXAMPLE/list_split%}
 * end
 *
 * @param <T>
 */
public class CommandListSplit<T> extends AbstractCommand<ListValue<ListValue<T>>> {
    public CommandListSplit(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<ListValue<ListValue<T>>> execute(Context context) {
        final var list = nextArgument(context)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'first"));
        final var index = nextArgument(context, this::toLong).map(Math::toIntExact)
                .orElseThrow(() -> new IllegalArgumentException("The index is missing"));
        switch (list) {
            case ListValue<?> lv -> {
                if (lv.values().isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                final var firstList = new ListValue<T>(interpreter);
                for (int i = 0; i < lv.values().size() && i < index; i++) {
                    firstList.values().add((Value<T>) lv.values().get(i));
                }
                final var secondList = new ListValue<T>(interpreter);
                for (int i = index; i < lv.values().size(); i++) {
                    secondList.values().add((Value<T>) lv.values().get(i));
                }
                final var newList = new ListValue<ListValue<T>>(interpreter);
                newList.values().add(new SimpleValue<>(firstList));
                newList.values().add(new SimpleValue<>(secondList));
                return new SimpleValue<>(newList);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
