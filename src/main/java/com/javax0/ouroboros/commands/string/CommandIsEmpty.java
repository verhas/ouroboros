package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_isEmpty
 * {%COMMAND isEmpty%}
 * Check if the string is empty.
 * The command returns true if the string is empty.
 * end
 */
public class CommandIsEmpty extends AbstractCommand<Boolean> {

    public CommandIsEmpty(Interpreter interpreter) {
        set(interpreter);
    }
    @Override
    public Value<Boolean> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.isEmpty());
    }


}
