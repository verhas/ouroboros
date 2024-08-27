package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.constant.DoubleConstant;
import com.javax0.ouroboros.commands.constant.LongConstant;
import com.javax0.ouroboros.commands.constant.NumericConstant;

import java.util.regex.Pattern;

public class NumericLexer extends AbstractCommand<NumericConstant> {

    private static final Pattern[] patterns = new Pattern[]{
            Pattern.compile("^([+-]?(?:0[Bb])?\\d[\\d_]*[lL]?)")
            ,
            Pattern.compile("^([+-]?0[Xx][\\da-fA-F][\\d_a-fA-F]*[lL]?)")
            ,
            Pattern.compile("^([+-]?\\d+(?:\\.\\d*)?(?:[eE][+-]?\\d+)?[fFdD]?)")
            ,
            Pattern.compile("^([+-]?0[Xx][\\da-fA-F]*(?:\\.[\\da-fA-F]*)?[pP][+-]?\\d+[fFdD]?)")
            ,
    };

    // TODO: implement sign and float and hex and so on
    @Override
    public Value<NumericConstant> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        final var sb = new StringBuilder(input);
        final var literals = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            final var matcher = patterns[i].matcher(sb.toString());
            if (matcher.find()) {
                literals[i] = matcher.group(1);
            } else {
                literals[i] = "";
            }
        }

        int maxLength = literals[0].length();
        int index = 0;
        for (int i = 1; i < patterns.length; i++) {
            if (maxLength < literals[i].length()) {
                index = i;
                maxLength = literals[i].length();
            }
        }
        if (maxLength == 0) {
            return null;
        }
        sb.delete(0, maxLength);
        source.update(sb.toString());
        return index > 1 ? new SimpleValue<>(new DoubleConstant(Double.parseDouble(literals[index])))
                : new SimpleValue<>(new LongConstant(Long.parseLong(literals[index])));
    }
}
