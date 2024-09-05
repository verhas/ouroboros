package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

/**
 * command_or
 * {%COMMAND or%}
 * Logical OR.
 * The command calculates the logical OR of the two boolean values.
 * The evaluation is NOT short-circuiting.
 * <p>
 * end
 */
public class CommandOr extends AbstractCommandLogicalBinop {

    public CommandOr(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    boolean binop(boolean left, boolean right) {
        return left || right;
    }
}
