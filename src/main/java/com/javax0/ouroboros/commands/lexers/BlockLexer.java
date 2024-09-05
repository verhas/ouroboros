package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.CommandBlock;
import com.javax0.ouroboros.commands.pseudo.CommandBlockClose;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.ArrayList;

/**
 * command_lexer_block
 * {%COMMAND lexer: block%}
 * <p>
 * A lexer that consumes a block of commands.
 * <p>
 * The block of commands starts with a `{` character and ends with a `}` character.
 * When the parser finds the opening `{` character it starts to read the commands with a recursive call until the next command is a CommandBlockClose.
 * That way blocks can be nested.
 * <p>
 * end
 *
 * @param <T>
 */
public class BlockLexer<T> extends AbstractCommand<CommandBlock<T>> {

    @Override
    public Value<CommandBlock<T>> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        final var blocks = new ArrayList<Block>();
        if (!input.isEmpty() && input.charAt(0) == '{') {
            source.update(input.substring(1));
            final var fetch = getFetcher(context);
            while (!source.get().isEmpty()) {
                final var block = fetch.execute(context).get();
                if (block instanceof CommandBlockClose) {
                    break;
                }
                blocks.add(block);
            }
            return new SimpleValue<>(new CommandBlock<>(interpreter, new SimpleBlock(blocks)));
        }
        return null;
    }

    private Command<Block> getFetcher(Context context) {
        return context.variable("$fetch")
                .map(Value::get)
                .map(SafeCast.to(it -> (Command<Block>) it))
                .orElseThrow(() -> new IllegalArgumentException("No block fetcher"));
    }

}
