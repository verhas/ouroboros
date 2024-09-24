package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_replace
 * {%COMMAND replace%}
 * Replace the target string with the replacement string in the string.
 * The command returns the string where the target string is replaced with the replacement string.
 * end
 */
public class CommandReplace extends AbstractCommand<String> {

    public CommandReplace(Interpreter interpreter) {
super(interpreter);
    }
    @Override
    public Value<String> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        final var target = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        final var replacement = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.replace(target,replacement ));
    }


}
