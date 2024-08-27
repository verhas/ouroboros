package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

public class CommandField<T> extends AbstractCommand<T> {
    public CommandField(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var objectArg = interpreter.pop();
        final ObjectValue object;
        if (objectArg instanceof Command<?> command) {
            final var obj = interpreter.evaluate(context, command).get();
            if (obj instanceof ObjectValue objectValue) {
                object = objectValue;
            } else {
                throw new IllegalArgumentException("The first argument of 'field' should be an object");
            }
        } else {
            throw new IllegalArgumentException("The second argument of 'set' should be an object");
        }

        final var nameArg = interpreter.pop();
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else if (nameArg instanceof Command<?> cmd) {
            name = interpreter.evaluate(context, cmd).get().toString();
        } else {
            throw new IllegalArgumentException("The second argument of 'field' should be a name");
        }


        return (Value<T>)object.get(name);
    }

    @Override
    public String toString() {
        return "CommandSet";
    }
}
