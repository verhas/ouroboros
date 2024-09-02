package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.Optional;

/**snippet command_sets
 * {%COMMAND sets%}
 *
 * Set the remaining source that was not processed yet.
 * end snippet
 */
public class CommandSets extends AbstractCommand<Void> {

    public CommandSets(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Void> execute(Context context) {
        final var source = CommandSource.getSource(interpreter);
        final var newSource = nextArgument(context,this::toString).orElse("");
        source.update(newSource);
        return null;
    }

}
