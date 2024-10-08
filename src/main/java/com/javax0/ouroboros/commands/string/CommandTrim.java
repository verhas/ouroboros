package com.javax0.ouroboros.commands.string;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
/**
 * command_trim
 * {%COMMAND trim%}
 * Trim the string.
 * The command returns the string trimmed.
 * end
 */
public class CommandTrim extends AbstractCommand<String> {

    public CommandTrim(Interpreter interpreter) {
super(interpreter);
    }
    @Override
    public Value<String> execute(Context context) {
        final var string = nextArgument(context,this::toString).orElseThrow(() -> new IllegalArgumentException("String is missing"));
        return new SimpleValue<>(string.trim());
    }


}
