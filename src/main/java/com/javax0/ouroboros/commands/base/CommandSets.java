package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_sets
 * {%COMMAND sets%}
 * <p>
 * Set the remaining source that was not processed yet.
 * end
 */
public class CommandSets extends AbstractCommand<Void> {

    public CommandSets(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Void> execute(Context context) {
        final var source = CommandSource.getSource(interpreter);
        final var newSource = nextArgument(context, this::toString).orElse("");
        source.update(newSource);
        return null;
    }

}
