package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandArg<T> extends AbstractCommand<T> {

    public CommandArg(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var savedBlocks = interpreter.up();
        try {
            final var object = interpreter.pop();
            return new SimpleValue<>((T) object);
        } finally {
            interpreter.down(savedBlocks);
        }
    }

    @Override
    public String toString() {
        return "shift";
    }
}
