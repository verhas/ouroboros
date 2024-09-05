package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
/**
 * command_sub
 * {%COMMAND sub%}
 * Subtract two values.
 * The command subtracts the second and optionally the further values from the first and returns the result.
 * <p>
 * end
 */
public class CommandSub<T> extends AbstractCommandBinop<T> {

    public CommandSub(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    T binop(Long left, Long right) {
        return (T)Long.valueOf(left - right);
    }


    @Override
    T binop(Double left, Double right) {
        return (T)Double.valueOf(left - right);
    }

    @Override
    T binop(BigInteger left, BigInteger right) {
        return (T)left.subtract(right);
    }

    @Override
    T binop(BigDecimal left, BigDecimal right) {
        return (T)left.subtract(right);
    }

    @Override
    T binop(String left, String right) {
        if( left.contains(right) ){
            return (T)left.replaceFirst(Pattern.quote(right), "");
        }
        return (T)left;
    }


}
