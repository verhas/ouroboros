package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandEval<T> extends AbstractCommand<T> {

    public CommandEval(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var value = getArg(context).get();
        if (value == null) {
            return null;
        } else {
            final var command = new CommandBlock<>(interpreter, new SimpleBlock(new ArrayList<>(List.of(new Source(interpreter, value.toString())))));
            return (Value<T>) command.execute(context);
        }
    }

    @Override
    public String toString() {
        return "shift";
    }
}
