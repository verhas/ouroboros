package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

/**
 * command_logical_and
 * {%COMMAND binop: and%}
 * <p>
 * Logical and operation.
 * <p>
 * end
 */
public class CommandAnd extends AbstractCommandLogicalBinop {

    public CommandAnd(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    boolean binop(boolean left, boolean right) {
        return left && right;
    }

}
