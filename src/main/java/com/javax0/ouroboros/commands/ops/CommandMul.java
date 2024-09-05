package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * command_mul
 * {%COMMAND mul%}
 * Multiply two or more values.
 * The command multiplies two or more values and returns the result.
 * <p>
 * end
 */
public class CommandMul<T> extends AbstractCommandBinop<T> {

    public CommandMul(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    T binop(Long left, Long right) {
        return (T) Long.valueOf(left * right);
    }


    @Override
    T binop(Double left, Double right) {
        return (T) Double.valueOf(left * right);
    }

    @Override
    T binop(BigInteger left, BigInteger right) {
        return (T) left.multiply(right);
    }

    @Override
    T binop(BigDecimal left, BigDecimal right) {
        return (T) left.multiply(right);
    }

    @Override
    T binop(String left, String right) {
        try {
            final var factor = Long.parseLong(right);
            return (T) (left.repeat(Math.toIntExact(factor)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot multiply '" + left + "' by '" + right + "'");
        }
    }


}
