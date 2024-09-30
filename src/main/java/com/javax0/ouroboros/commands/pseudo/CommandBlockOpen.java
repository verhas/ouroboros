package com.javax0.ouroboros.commands.pseudo;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Value;

public class CommandBlockOpen implements Command<Void> {

    @Override
    public Value<Void> execute(Context context) {
        throw new IllegalArgumentException("CommandBlockOpen does not implement execute(...)");
    }


    @Override
    public String toString() {
        return "{";
    }
}
