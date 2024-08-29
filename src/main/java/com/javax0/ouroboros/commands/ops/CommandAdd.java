package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;

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
