package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.List;

@AbstractCommand.Arguments(3)
public class CommandMethod extends AbstractCommand<Void> {
    public CommandMethod(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context, List<Block> arguments) {
        final var objectArg = arguments.getFirst();
        final ObjectValue object;
        if (objectArg instanceof Command<?> command) {
            final var obj = interpreter.evaluate(context, command).get();
            if (obj instanceof ObjectValue objectValue) {
                object = objectValue;
            } else {
                throw new IllegalArgumentException("The first argument of 'setf' should be an object");
            }
        } else {
            throw new IllegalArgumentException("The first argument of 'setf' should be an object");
        }

        final var nameArg = arguments.get(1);
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }

        final var value = new SimpleValue<>(arguments.get(2));
        object.get().put(name, value);
        return null;
    }

    @Override
    public String toString() {
        return "CommandFun";
    }
}
