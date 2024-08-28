package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;

import java.math.BigDecimal;
import java.math.BigInteger;


public abstract class AbstractCommandBinop<T> extends AbstractCommand<T> {

    public AbstractCommandBinop(Interpreter interpreter) {
        set(interpreter);
    }

    T binop(Long left, Long right) {
        throw new IllegalArgumentException("Cannot perform " + this + " binary operation on Long");
    }

    T binop(Double left, Double right) {
        throw new IllegalArgumentException("Cannot perform " + this + " binary operation on Double");
    }

    T binop(BigInteger left, BigInteger right) {
        throw new IllegalArgumentException("Cannot perform " + this + " binary operation on BigInteger");
    }

    T binop(BigDecimal left, BigDecimal right) {
        throw new IllegalArgumentException("Cannot perform " + this + " binary operation on BigDecimal");
    }

    T binop(String left, String right) {
        throw new IllegalArgumentException("Cannot perform " + this + " binary operation on String");
    }


    protected Context context;

    @Override
    public Value<T> execute(Context context) {
        this.context = context;
        var first = interpreter.pop();
        if (first instanceof BareWord<?> bw && "*".equals(bw.get())) {
            first = interpreter.pop();
            T left = (T) interpreter.evaluate(context, first).get();
            Block second;
            var accumulator = true;
            T retval = null;
            while ((second = interpreter.pop()) != null) {
                final var rightValue = interpreter.<T>evaluate(context, second);
                if (rightValue == null) {
                    break;
                }
                final T right = rightValue.get();
                var result = binop(left, right);
                if (result instanceof Boolean bool) {
                    accumulator = accumulator && bool;
                    retval = (T) (Boolean) accumulator;
                } else {
                    left = result;
                    retval = left;
                }
            }
            return new SimpleValue<>(retval);
        } else {
            final T left = (T) interpreter.evaluate(context, first).get();
            final var second = interpreter.pop();
            final T right = (T) interpreter.evaluate(context, second).get();
            return new SimpleValue<>(
                    binop(left, right));
        }
    }

    private T binop(T left, T right) {
        return switch (left) {
            case Long lng -> binop(lng, toLong(right));
            case Double dbl -> binop(dbl, toDouble(right));
            case BigInteger bigi -> binop(bigi, toBigInteger(right));
            case BigDecimal bigd -> binop(bigd, toBigDecimal(right));
            case String str -> binop(str, toString(right));
            case null -> throw new IllegalArgumentException("Cannot perform binary operation on null");
            default -> throw new IllegalArgumentException("Cannot perform binary operation on " + left.getClass());
        };
    }
}
