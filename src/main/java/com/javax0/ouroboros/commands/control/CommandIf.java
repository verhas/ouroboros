package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;


public class CommandIf<T> extends AbstractCommand<T> {

    public CommandIf(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var condition = toBoolean(interpreter.evaluate(context, interpreter.pop()).get());
        final var then = interpreter.pop();
        final var otherwise = interpreter.pop();
        final var block = condition ? then : otherwise;
        if( block == null ){
            return null;
        }
        return interpreter.evaluate(context, block);
    }

}
