package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.regex.Pattern;

public class CommandMatch extends AbstractCommand<Boolean> {
    public CommandMatch(Interpreter interpreter) {
        set(interpreter);
    }
    @Override
    public Value<Boolean> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        final var regex = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("Regex is missing"));
        final var pattern = Pattern.compile(regex);
        return new SimpleValue<>(pattern.matcher(string).matches());
    }

    @Override
    public String toString() {
        return "CommandIsBlank";
    }
}
