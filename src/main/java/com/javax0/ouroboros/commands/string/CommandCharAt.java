package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandCharAt extends AbstractCommand<String> {

    public CommandCharAt(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<String> execute(Context context) {
        final var index = this.<Long>nextArgument(context, this::toLong).orElseThrow(() -> new IllegalArgumentException("Index is missing"));
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.substring(Math.toIntExact(index),Math.toIntExact(index+1)));
    }

    @Override
    public String toString() {
        return "CommandCharAt";
    }
}
