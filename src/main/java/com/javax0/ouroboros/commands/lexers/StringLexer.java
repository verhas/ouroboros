package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.Source;
import com.javax0.ouroboros.commands.constant.StringConstant;

public class StringLexer extends AbstractCommand<StringConstant> {
    // TODO: implement complex string parsing
    @Override
    public Value<StringConstant> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && input.charAt(0) == '"') {
            int i = 1;
            while (i < input.length() && input.charAt(i) != '"') {
                i++;
            }
            final var word = input.substring(1, i);
            final var rest = input.substring(i + 1);
            source.update(rest);
            return new SimpleValue<>(new StringConstant(word));
        }
        return null;
    }
}
