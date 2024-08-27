package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class CommandQuote<T> extends AbstractCommand<T> {

    public CommandQuote(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var arg = interpreter.pop();
        return new SimpleValue<>((T) arg);
    }

    @Override
    public String toString() {
        return "shift";
    }
}
