package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;


public class CommandSet extends AbstractCommand<Void> {
    public CommandSet(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final var nameArg = interpreter.pop();
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else if (nameArg instanceof Command<?> command) {
            name = interpreter.evaluate(context, command).get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }
        final var value = interpreter.evaluate(context, interpreter.pop());
        context.set(name, value);
        return null;
    }

    @Override
    public String toString() {
        return "CommandSet";
    }
}
