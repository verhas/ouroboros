package com.javax0.ouroboros.commands.ops;

import com.javax0.ouroboros.Interpreter;

public class CommandAnd extends AbstractCommandLogicalBinop {

    public CommandAnd(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    boolean binop(boolean left, boolean right) {
        return left && right;
    }
    @Override
    boolean finished(boolean start) {
        return !start;
    }



}
