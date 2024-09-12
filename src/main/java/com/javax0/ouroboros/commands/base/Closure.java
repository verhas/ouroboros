package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * A closure is a block of code that can be executed in a context that has a set of variables.
 *
 * @param <T>
 */
public class Closure<T> extends AbstractCommand<T> {
    final Block code;
    final ObjectValue variables;

    public Closure(Interpreter interpreter, Block code, ObjectValue variables) {
        this.code = code;
        this.variables = variables;
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        try {
            context.down(variables);
            return interpreter.evaluate(context, code);
        }finally {
            context.up();
        }
    }
}
