package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;


public class CommandMethod extends AbstractCommand<Void> {
    public CommandMethod(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final var objectArg = interpreter.pop();
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

        final var nameArg = interpreter.pop();
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }

        final var value = new SimpleValue<>(interpreter.pop());
        object.put(name, value);
        return null;
    }

    @Override
    public String toString() {
        return "CommandFun";
    }
}
