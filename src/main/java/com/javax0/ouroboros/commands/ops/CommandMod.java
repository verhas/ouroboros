package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * command_mod
 * {%COMMAND mod%}
 * Calculate the modulus of two numbers.
 * <p>
 * The command calculates the modulus of the two numbers. The modulus is the remainder of the division of the first number by the second.
 * <p>
 * end
 */
public class CommandMod<T> extends AbstractCommandBinop<T> {

    public CommandMod(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    T binop(Long left, Long right) {
        return (T) Long.valueOf(left % right);
    }


    @Override
    T binop(Double left, Double right) {
        return (T) Double.valueOf(left % right);
    }

    @Override
    T binop(BigInteger left, BigInteger right) {
        return (T) left.mod(right);
    }

    @Override
    T binop(BigDecimal left, BigDecimal right) {
        return (T) left.remainder(right);
    }


}
