package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_replaceAll
 * {%COMMAND replaceAll%}
 * Replace the target string with the replacement string in the string.
 * The first argument is the source string, the second argument is a regular expression, and the third argument is the replacement string.
 * The command returns the string where all matching occurrence of the regular expression is replaced with the replacement string.
 * end
 */
public class CommandReplaceAll extends AbstractCommand<String> {

    public CommandReplaceAll(Interpreter interpreter) {
        set(interpreter);
    }
    @Override
    public Value<String> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        final var target = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("target is missing"));
        final var replacement = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("replacement is missing"));
        return new SimpleValue<>(string.replaceAll(target,replacement ));
    }


}
