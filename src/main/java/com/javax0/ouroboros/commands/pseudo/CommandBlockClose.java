package com.javax0.ouroboros.commands.pseudo;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;

public class CommandBlockClose implements Command<Void> {
    @Override
    public void set(Interpreter interpreter) {
    }

    @Override
    public Value<Void> execute(Context context) {
        throw new IllegalArgumentException("CommandBlockClose does not implement execute(...)");
    }


    @Override
    public String toString() {
        return "}";
    }
}
