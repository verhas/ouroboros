package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;

/**
 * command_lexer_space
 * {%COMMAND lexers: space%}
 * <p>
 * Fetches one or more white space from the input.
 * It returns `null` so that the white spaces are simple token separators and no command is created from them.
 * end
 *
 * @param <T>
 */
public class SpaceLexer<T> extends AbstractCommand<BareWord<T>> {

    @Override
    public Value<BareWord<T>> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && Character.isWhitespace(input.charAt(0))) {
            int i = 1;
            while (i < input.length() && Character.isSpaceChar(input.charAt(i))) {
                i++;
            }
            final var rest = input.substring(i);
            source.update(rest);
            return new SimpleValue<>(null);
        }
        return null;
    }
}
