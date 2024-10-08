package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;
import com.javax0.ouroboros.utils.SafeCast;

/**
 * command_lexer_symbol
 * {%COMMAND lexers: $symbol%}
 * <p>
 * Fetches one or more symbol characters from the input.
 * It returns a BareWord that is a symbol.
 * <p>
 * A symbol is a character or a sequence of characters that are not a valid Java identifier, and do not contain whitespace or digits or the characters '{' and '}'.
 * <p>
 * end
 *
 * @param <T>
 */
public class SymbolLexer<T> extends AbstractCommand<BareWord<T>> {

    public SymbolLexer(Interpreter interpreter) {
        super(interpreter);
    }

    @Override
    public Value<BareWord<T>> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (startAsANumber(input)) {
            return null;
        }
        final var singleCharacterSymbol = context.variable("$symbolChars")
                .map(Value::get)
                .map(SafeCast.to(String.class))
                .orElse("");
        if( singleCharacterSymbol.indexOf(input.charAt(0)) != -1 ){
            final var word = input.substring(0, 1);
            final var rest = input.substring(1);
            source.update(rest);
            return new SimpleValue<>(new BareWord<>(interpreter, word));
        }
        int i = 0;
        while (isSymbolCharacter(input, i)) {
            i++;
        }
        if (i > 0) {
            final var word = input.substring(0, i);
            final var rest = input.substring(i);
            source.update(rest);
            return new SimpleValue<>(new BareWord<>(interpreter, word));
        }
        return null;
    }

    private boolean startAsANumber(String input) {
        return input.length() >= 2 && (input.charAt(0) == '-' || input.charAt(0) == '+') && Character.isDigit(input.charAt(1));
    }

    private boolean isSymbolCharacter(String input, int i) {
        if (i >= input.length()) {
            return false;
        }
        final var ch = input.charAt(i);
        return !Character.isJavaIdentifierStart(ch)
                && !Character.isJavaIdentifierPart(ch)
                && !Character.isWhitespace(ch)
                && !Character.isDigit(ch)
                //&& ch != '{'
                //&& ch != '}'
                ;
    }
}
