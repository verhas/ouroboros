package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

public abstract class AbstractCommandLogicalBinop extends AbstractCommand<Boolean> {
    public AbstractCommandLogicalBinop(Interpreter interpreter) {
super(interpreter);
    }

    abstract boolean binop(boolean left, boolean right);

    @Override
    public Value<Boolean> execute(Context context) {
        var first = interpreter.pop();
        if (isVararg(first)) {
            first = interpreter.pop();
            var left = toBoolean(interpreter.evaluate(context, first).get());
            Block second;
            while ((second = interpreter.pop()) != null) {
                final var rightValue = interpreter.evaluate(context, second);
                if (rightValue == null) {
                    break;
                }
                final var right = rightValue.get();
                left = binop(left, toBoolean(right));
            }
            return new SimpleValue<>(left);
        } else {
            final var left = toBoolean(interpreter.evaluate(context, first).get());
            final var second = interpreter.pop();
            final var right = toBoolean(interpreter.evaluate(context, second).get());
            return new SimpleValue<>(binop(left, right));
        }
    }
}
