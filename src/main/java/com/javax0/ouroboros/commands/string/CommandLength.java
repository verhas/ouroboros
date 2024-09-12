package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.list.ListValue;

/**
 * command_length
 * {%COMMAND length%}
 * Get the length of the string or a list.
 * The command returns the length of the string or a list.
 * end
 */
public class CommandLength extends AbstractCommand<Long> {
    public CommandLength(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Long> execute(Context context) {
        final var arg = nextArgument(context).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return switch (arg) {
            case ListValue<?> lv -> {
                if (lv.values().isEmpty()) {
                    throw new IllegalArgumentException("The list is empty");
                }
                yield new SimpleValue<>((long) lv.values().size());
            }
            case String s -> new SimpleValue<>((long) s.length());
            case Source s -> new SimpleValue<>((long) s.toString().length());
            default -> throw new IllegalStateException("Unexpected value: " + arg);
        };
    }


}
