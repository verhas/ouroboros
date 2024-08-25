package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.List;

@AbstractCommand.Arguments(2)
public class CommandSet extends AbstractCommand<Void> {
    public CommandSet(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context, List<Block> arguments) {
        final var nameArg = arguments.getFirst();
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else if (nameArg instanceof Command<?> command) {
            name = interpreter.evaluate(context, command).get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }
        final var value = interpreter.evaluate(context, arguments.getLast());
        context.set(name, value);
        return null;
    }

    @Override
    public String toString() {
        return "CommandSet";
    }
}
