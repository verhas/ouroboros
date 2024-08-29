package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;

public abstract class AbstractCommandLogicalBinop extends AbstractCommand<Boolean> {
    public AbstractCommandLogicalBinop(Interpreter interpreter) {
        set(interpreter);
    }

    abstract boolean binop(boolean left, boolean right);

    abstract boolean finished(boolean start);

    @Override
    public Value<Boolean> execute(Context context) {
        var first = interpreter.pop();
        if (isVararg(first)) {
            first = interpreter.pop();
            var left = toBoolean(interpreter.evaluate(context, first).get());
            Block second;
            if (finished(left)) {
                ignoreBlocks();
                return new SimpleValue<>(left);
            }
            while ((second = interpreter.pop()) != null) {
                final var rightValue = interpreter.evaluate(context, second);
                if (rightValue == null) {
                    break;
                }
                final var right = rightValue.get();
                left = binop(left, toBoolean(right));
                if (finished(left)) {
                    ignoreBlocks();
                }
            }
            return new SimpleValue<>(left);
        } else {
            final var left = toBoolean(interpreter.evaluate(context, first).get());
            final var second = interpreter.pop();
            if (finished(left)) {
                return new SimpleValue<>(left);
            } else {
                final var right = toBoolean(interpreter.evaluate(context, second).get());
                return new SimpleValue<>(binop(left, right));
            }
        }
    }

    private void ignoreBlocks() {
        Block rest = interpreter.pop();
        while (rest instanceof Command<?> || rest != null && !rest.subBlocks().isEmpty()) {
            rest = interpreter.pop();
        }
    }

}
