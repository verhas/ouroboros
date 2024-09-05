package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_tolower
 * {%COMMAND tolower%}
 * Convert the string to lower-case.
 * The command returns the string converted to lower-case.
 * end
 */
public class CommandTolower extends AbstractCommand<String> {

    public CommandTolower(Interpreter interpreter) {
        set(interpreter);
    }
    @Override
    public Value<String> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.toLowerCase());
    }


}
