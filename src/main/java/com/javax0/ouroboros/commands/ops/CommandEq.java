package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * command_binop_eq
 * {%COMMAND binop: eq%}
 * Compare two values.
 * The command compares two values and returns true if they are equal.
 * <p>
 * end
 */
public class CommandEq extends AbstractCommandBinop<Boolean> {

    public CommandEq(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    Boolean binop(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    Boolean binop(Double left, Double right) {
        return Objects.equals(left, right);
    }

    @Override
    Boolean binop(BigInteger left, BigInteger right) {
        return Objects.equals(left,right);
    }

    @Override
    Boolean binop(BigDecimal left, BigDecimal right) {
        return Objects.equals(left,right);
    }

    @Override
    Boolean binop(String left, String right) {
        return Objects.equals(left,right);
    }


}
