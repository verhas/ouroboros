package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.constant.LongConstant;
import com.javax0.ouroboros.Source;

public class NumericLexer extends AbstractCommand<LongConstant> {
    // TODO: implement sign and float and hex and so on
    @Override
    public Value<LongConstant> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && Character.isDigit(input.charAt(0))) {
            int i = 1;
            while (i < input.length() && Character.isDigit(input.charAt(i))) {
                i++;
            }
            final var word = input.substring(0, i);
            final var rest = input.substring(i);
            source.update(rest);
            return new SimpleValue<>(new LongConstant(Long.parseLong(word)));
        }
        return null;
    }
}
