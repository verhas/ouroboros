package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * command_eval
 * {%COMMAND eval%}
 * Evaluate the argument string as program code using the current syntax setup.
 * Note that the lexical analysers can be modified during the code execution.
 * This evaluation uses the lexical analysers that are currentlyin use at the execution level.
 * <p>
 * {%EXAMPLE/eval%}
 * <p>
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandEval<T> extends AbstractCommand<T> {

    public CommandEval(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var value = nextArgument(context, s -> s).orElse(null);
        if (value == null) {
            return null;
        } else {
            final var command = new CommandBlock<>(interpreter, new SimpleBlock(new ArrayList<>(List.of(new Source(interpreter, value.toString())))));
            return (Value<T>) command.execute(context);
        }
    }
}
