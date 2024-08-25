package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandShift<T> extends AbstractCommand<T> {

    public CommandShift(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var savedBlocks = interpreter.up();
        try {
            final var object = interpreter.pop();
            if (object instanceof Command<?> command) {
                return (Value<T>) command.execute(context);
            } else if (object instanceof Value<?> value) {
                return (Value<T>) value;
            } else {
                return new SimpleValue<>((T) object);
            }
        } finally {
            interpreter.down(savedBlocks);
        }
    }

    @Override
    public String toString() {
        return "shift";
    }
}
