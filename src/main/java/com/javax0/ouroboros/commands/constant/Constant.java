package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public abstract class Constant<T> extends AbstractCommand<T> implements Value<T> {

    protected Value<T> value;

    public Constant(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public Value<T> copy() {
        return value.copy();
    }

    @Override
    public Value<T> execute(Context context) {
        return value;
    }
}
