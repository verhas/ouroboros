package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;

/**
 * command_lexer
 * {%COMMAND lexers%}
 * <p>
 * Lexical analysers are also commands in the interpreter.
 * They read the source code and decide if they can read a token from the source.
 * If they can, they read the token and return it after modifying the source, chopping off the consumed part.
 * <p>
 * The lexical analysers are called one after the other until one of them can read a token.
 * <p>
 * When a lexical analyser returns a null value (not null, but a Value that contains null) it means that the analysis did eat some token, but it is to be ignored.
 * Typically, the space lexer returns a null value when it reads a space character.
 * <p>
 * When this happens, the lexical analysis starts over with the first lexer.
 * <p>
 * end
 *
 * @param <T>
 */


/**
 * command_lexer_bare_word
 * {%COMMAND lexer: $keyword%}
 * <p>
 * Get the next word from the source that is formally a valid identifier.
 * It will be a bare word that may represent a command or a value based on the context.
 * Return `null` if the next token is not a valid identifier.
 * <p>
 * end
 *
 * @param <T>
 */
public class BareWordLexer<T> extends AbstractCommand<BareWord<T>> {

    @Override
    public Value<BareWord<T>> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && Character.isJavaIdentifierStart(input.charAt(0))) {
            int i = 1;
            while (i < input.length() && Character.isJavaIdentifierPart(input.charAt(i))) {
                i++;
            }
            final var word = input.substring(0, i);
            final var rest = input.substring(i);
            source.update(rest);
            return new SimpleValue<>(new BareWord<>(interpreter, word));
        }
        return null;
    }
}
