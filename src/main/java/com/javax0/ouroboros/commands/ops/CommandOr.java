package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

public class CommandOr extends AbstractCommandLogicalBinop {

    public CommandOr(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    boolean binop(boolean left, boolean right) {
        return left || right;
    }
    @Override
    boolean finished(boolean start) {
        return start;
    }


}
