package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_lexer_space
 * {%COMMAND lexers: $space%}
 * <p>
 * Fetches one or more white spaces from the input.
 * It returns `null` so that the white spaces are simple token separators and no command is created from them.
 * <p>
 * Note that returning `null` and returning `null` value (an instance of `SimpleValue` with `null` value) are different things.
 * The `null` return value means that the lexer consumed no input.
 * The `null` simple value means that there were characters on the input that the lexer consumed, but there is no created command or value.
 * <p>
 * end
 *
 * @param <T>
 */
public class SpaceLexer<T> extends AbstractCommand<Void> {

    public SpaceLexer(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
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
