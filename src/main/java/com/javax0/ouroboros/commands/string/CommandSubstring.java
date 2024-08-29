package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandSubstring extends AbstractCommand<String> {
    public CommandSubstring(Interpreter interpreter) {
        set(interpreter);
    }
    @Override
    public Value<String> execute(Context context) {
        final var start = nextArgument(context, this::toLong).orElseThrow(() -> new IllegalArgumentException("Start is missing"));
        final var end = nextArgument(context, this::toLong).orElseThrow(() -> new IllegalArgumentException("End is missing"));
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.substring(Math.toIntExact(start),Math.toIntExact(end+1)));
    }


}
