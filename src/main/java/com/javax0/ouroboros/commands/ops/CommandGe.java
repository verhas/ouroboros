package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * command_binop_ge
 * {%COMMAND binop: ge%}
 * Compare two values for greater or equal.
 * The command compares two values and returns true if the first is greater or equal than the second, and optionally the further arguments.
 * <p>
 * end
 */
public class CommandGe extends AbstractCommandBinop<Boolean> {

    public CommandGe(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    Boolean binop(Long left, Long right) {
        return left >= right;
    }

    @Override
    Boolean binop(Double left, Double right) {
        return left >= right;
    }

    @Override
    Boolean binop(BigInteger left, BigInteger right) {
        return left.compareTo(right) >= 0;
    }

    @Override
    Boolean binop(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    @Override
    Boolean binop(String left, String right) {
        return left.compareTo(right) >= 0;
    }


}
