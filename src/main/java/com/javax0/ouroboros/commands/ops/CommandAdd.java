package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;
/**
 * command_binop_add
 * {%COMMAND binop: add%}
 * <p>
 * Add values.
 * The values can be numbers or strings.
 * If the values are numbers, then the result is the sum of the numbers.
 * If the values are strings, then the result is the concatenation of the strings.
 * <p>
 * {%EXAMPLE/putsadd%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandAdd<T> extends AbstractCommandBinop<T> {

    public CommandAdd(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    T binop(Long left, Long right) {

        return (T)Long.valueOf(left + right);
    }


    @Override
    T binop(Double left, Double right) {
        return (T)Double.valueOf(left + right);
    }

    @Override
    T binop(BigInteger left, BigInteger right) {
        return (T)left.add(right);
    }

    @Override
    T binop(BigDecimal left, BigDecimal right) {
        return (T)left.add(right);
    }

    @Override
    T binop(String left, String right) {
        return (T)(left + right);
    }


}
