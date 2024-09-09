package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_indexOf
 * {%COMMAND indexOf%}
 * Returnt he index of the string in the second string where it appears in the second string or -1 if the first string
 * is not part of the second.
 * end
 */
public class CommandIndexOf extends AbstractCommand<Long> {
    public CommandIndexOf(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Long> execute(Context context) {
        final var find = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(this.toLong(string.indexOf(find)));
    }


}
