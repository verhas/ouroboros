package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
/**
 * command_ne
 * {%COMMAND ne%}
 * Compare two values for not equal.
 * The command compares two values and returns true if the first is not equal to the second, and optionally the further arguments.
 * <p>
 * end
 */
public class CommandNe extends AbstractCommandBinop<Boolean> {

    public CommandNe(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    Boolean binop(Long left, Long right) {
        return !Objects.equals(left, right);
    }

    @Override
    Boolean binop(Double left, Double right) {
        return !Objects.equals(left, right);
    }

    @Override
    Boolean binop(BigInteger left, BigInteger right) {
        return !left.equals(right);
    }

    @Override
    Boolean binop(BigDecimal left, BigDecimal right) {
        return !left.equals(right);
    }

    @Override
    Boolean binop(String left, String right) {
        return !left.equals(right);
    }


}
