package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_substring
 * {%COMMAND substring%}
 * Get the substring of the string.
 * The command returns the substring of the string.
 * <p>
 * The first argument is the start index, the second argument is the end index.
 * The third argument is the string.
 * <p>
 * If the second argument is `*` then the substring is taken from the start index to the end of the string.
 * end
 */
public class CommandSubstring extends AbstractCommand<String> {
    public CommandSubstring(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        final var start = nextArgument(context, this::toLong).orElseThrow(() -> new IllegalArgumentException("Start is missing"));
        final var end = interpreter.pop();
        final var string = nextArgument(context, this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        long endIndex;
        if (isVararg(end)) {
            endIndex = string.length();
        } else {
            endIndex = nextArgument(context, end, this::toLong).orElseThrow(() -> new IllegalArgumentException("End is missing"));
        }
        return new SimpleValue<>(string.substring(Math.toIntExact(start), Math.toIntExact(endIndex)));
    }


}
