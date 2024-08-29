package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

/**
 * Set a field in an object.
 */
public class CommandSetf extends AbstractCommand<Void> {
    public CommandSetf(Interpreter interpreter) {
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
        } else if (nameArg instanceof Command<?> cmd) {
            name = interpreter.evaluate(context, cmd).get().toString();
        } else {
            throw new IllegalArgumentException("The second argument of 'setf' should be a name");
        }

        final var value = interpreter.evaluate(context, interpreter.pop());
        object.put(name, value);
        return null;
    }


}
