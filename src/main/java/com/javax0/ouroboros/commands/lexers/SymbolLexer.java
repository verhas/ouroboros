package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;

public class SymbolLexer<T> extends AbstractCommand<BareWord<T>> {

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
        return !Character.isUnicodeIdentifierStart(ch) &&
                !Character.isUnicodeIdentifierPart(ch) &&
                !Character.isWhitespace(ch) &&
                !Character.isDigit(ch) &&
                ch != '{' &&
                ch != '}' ;
    }
}
