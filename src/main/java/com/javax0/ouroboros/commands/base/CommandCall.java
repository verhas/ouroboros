package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;


public class CommandCall<T> extends AbstractCommand<T> {
    public CommandCall(Interpreter interpreter) {
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
                throw new IllegalArgumentException("The second argument of 'set' should be an object");
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
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }

        final var fun = object.get(name).get();

        if (fun instanceof Command<?> funCmd) {
            final var oldThis = context.variable("this");
            try {
                context.set("this", new SimpleValue<>(object));
                return (Value<T>) funCmd.execute(context);
            } finally {
                if (oldThis.isPresent()) {
                    context.set("this", oldThis.get());
                } else {
                    context.remove("this");
                }
            }
        } else if (object instanceof Value<?> value) {
            return (Value<T>) value;
        } else {
            return new SimpleValue<>((T) object);
        }
    }

    @Override
    public String toString() {
        return "CommandSet";
    }
}
