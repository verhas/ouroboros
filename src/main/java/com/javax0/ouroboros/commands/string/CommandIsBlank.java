package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_isBlank
 * {%COMMAND isBlank%}
 * Check if the string is blank.
 * The command returns true if the string is blank.
 * end
 */
public class CommandIsBlank extends AbstractCommand<Boolean> {
    public CommandIsBlank(Interpreter interpreter) {
super(interpreter);
    }


    @Override
    public Value<Boolean> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.isBlank());
    }


}
