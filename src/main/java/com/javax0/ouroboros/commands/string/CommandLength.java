package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_length
 * {%COMMAND length%}
 * Get the length of the string.
 * The command returns the length of the string.
 * end
 */
public class CommandLength extends AbstractCommand<Long> {
    public CommandLength(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Long> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(this.toLong(string.length()));
    }


}
