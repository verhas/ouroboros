package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.utils.SafeCast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * command_binop_div
 * {%COMMAND binop: div%}
 * <p>
 * Divide values.
 * The values can only be numbers.
 * <p>
 * {%EXAMPLE/putsdiv%}
 * <p>
 * When dividing BigDecimal values the scale and the rounding is defined by the local variables `$scale` and `$round`.
 * The default scale is 2 and the default rounding is `HALF_UP`.
 * <p>
 * Rounding modes are
 * <p>
 * * `UP`
 * * `DOWN`
 * * `CEILING`
 * * `FLOOR`
 * * `HALF_UP`
 * * `HALF_DOWN`
 * * `HALF_EVEN`, and
 * * `UNNECESSARY`
 * end
 */
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
        final int scale = context.variable("$scale").map(Value::get).map(SafeCast.to(Long.class))
                .map(Math::toIntExact).orElse(2);
        final RoundingMode roundingMode = context.variable("$round")
                .map(Value::get)
                .map(Object::toString)
                .map(String::toUpperCase)
                .map(RoundingMode::valueOf)
                .orElse(RoundingMode.HALF_UP);
        return (T) left.divide(right, scale, roundingMode);
    }


}
