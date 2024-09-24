package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_toupper
 * {%COMMAND toupper%}
 * Convert the string to upper-case.
 * The command returns the string converted to upper-case.
 * end
 */
public class CommandToupper extends AbstractCommand<String> {
    public CommandToupper(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        final var string = nextArgument(context, this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.toUpperCase());
    }


}
