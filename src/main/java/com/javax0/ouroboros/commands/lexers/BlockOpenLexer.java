package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.pseudo.CommandBlockClose;
import com.javax0.ouroboros.commands.pseudo.CommandBlockOpen;

/**
 * command_lexer_block_open
 * {%COMMAND lexer: $blockOpen%}
 * <p>
 * A lexer that consumes the opening block character '{'.
 * It returns a CommandBlockOpen command, which should and never will execute.
 * It is a technical placeholder.
 * <p>
 * end
 *
 * @param <T>
 */
public class BlockOpenLexer<T> extends AbstractCommand<CommandBlockOpen> {

    private static final Value<CommandBlockOpen> instance = new SimpleValue<>(new CommandBlockOpen());

    public BlockOpenLexer(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public Value<CommandBlockOpen> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && input.charAt(0) == '{') {
            final var rest = input.substring(1);
            source.update(rest);
            return instance;
        }
        return null;
    }

}
