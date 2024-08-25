package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.List;

@AbstractCommand.Arguments(2)
public class CommandField<T> extends AbstractCommand<T> {
    public CommandField(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context, List<Block> arguments) {
        final var nameArg = arguments.get(1);
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else if (nameArg instanceof Command<?> command) {
            name = interpreter.evaluate(context, command).get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }

        final var objectArg = arguments.getFirst();
        final ObjectValue object;
        if (objectArg instanceof Command<?> command) {
            final var obj = interpreter.evaluate(context, command).get();
            if (obj instanceof ObjectValue objectValue) {
                object = objectValue;
            } else {
                throw new IllegalArgumentException("The second argument of 'set' should be an object");
            }
        } else {
            throw new IllegalArgumentException("The second argument of 'set' should be an object");
        }

        return (Value<T>)object.get().get(name);
    }

    @Override
    public String toString() {
        return "CommandSet";
    }
}
