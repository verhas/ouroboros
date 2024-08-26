package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class CommandDiv<T> extends AbstractCommandBinop<T> {

    public CommandDiv(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    T binop(Long left, Long right) {
        return (T) Long.valueOf(left / right);
    }


    @Override
    T binop(Double left, Double right) {
        return (T) Double.valueOf(left / right);
    }

    @Override
    T binop(BigInteger left, BigInteger right) {
        return (T) left.divide(right);
    }

    @Override
    T binop(BigDecimal left, BigDecimal right) {
        final int scale = context.variable("$scale").map(Value::get).filter(Long.class::isInstance).map(Long.class::cast)
                .map(Math::toIntExact).orElse(2);
        final RoundingMode roundingMode = context.variable("$round")
                .map(Value::get)
                .map(Object::toString)
                .map(String::toUpperCase)
                .map(RoundingMode::valueOf)
                .orElse(RoundingMode.HALF_UP);
        return (T) left.divide(right, scale, roundingMode);
    }

    @Override
    public String toString() {
        return "CommandAdd";
    }
}
