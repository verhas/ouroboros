package com.javax0.ouroboros.commands;

import com.javax0.ouroboros.*;

import java.util.List;

public class BlockFetch extends AbstractCommand<Block> {

    public BlockFetch(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Block> execute(Context context) {
        final var lexers = context.<List<Command<Block>>>variable("$lex").orElseThrow(
                () -> new IllegalArgumentException("No lexer registered")
        );
        while (interpreter.source() != null) {
            Value<Block> result = null;
            for (final var lexer : lexers.get()) {
                result = lexer.execute(context);
                if (result != null) {
                    break;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("Cannot lex the block: " + interpreter.source().execute(context).get());
            }
            if (result.get() != null) {
                return result;
            }
        }
        return null;
    }
}