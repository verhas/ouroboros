package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandIsBlank extends AbstractCommand<Boolean> {
    public CommandIsBlank(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Boolean> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.isBlank());
    }


}
