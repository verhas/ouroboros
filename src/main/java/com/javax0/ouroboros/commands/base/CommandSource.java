package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.Optional;

/**
 * command_source
 * {%COMMAND source%}
 * Get the remaining source that was not processed yet.
 * end
 */
public class CommandSource extends AbstractCommand<Source> {

    public CommandSource(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<Source> execute(Context context) {
        return new SimpleValue<>(getSource(interpreter));
    }

    static Source getSource(Interpreter interpreter) {
        final var store = new ArrayList<Interpreter.State>();
        Interpreter.State state;
        while (interpreter.source() == null && (state = interpreter.up()) != null) {
            store.add(state);
        }
        try {
            return Optional.ofNullable(interpreter.source())
                    .orElse(null);
        } finally {
            store.reversed().forEach(interpreter::down);
        }
    }

}
