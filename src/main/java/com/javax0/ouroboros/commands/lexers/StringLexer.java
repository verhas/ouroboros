package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.constant.StringConstant;

/**
 * command_lexer_string
 * {%COMMAND lexer: $string%}
 * <p>
 * Fetches a string constant from the input.
 * It can be a simple string or a multi-line string.
 * <p>
 * Multi-line strings start with three `"` characters and they so not need escaping for single `"` quotes
 * end
 */
public class StringLexer extends AbstractCommand<StringConstant> {

    public static final String MULTI_LINE_STRING_DELIMITER = "\"\"\"";
    private static final int MLSD_LENGTH = MULTI_LINE_STRING_DELIMITER.length();
    private static final char ENCLOSING_CH = '"';

    private static String getString(StringBuilder input) {
        if (input.length() < 2) throw new IllegalArgumentException("String has to be at least two characters long.");
        if (input.length() >= MLSD_LENGTH && input.subSequence(0, MLSD_LENGTH).equals(MULTI_LINE_STRING_DELIMITER)) {
            return getMultiLineString(input);
        } else {
            return getSimpleString(input);
        }
    }

    private static String getMultiLineString(StringBuilder input) {
        final var output = new StringBuilder();
        input.delete(0, MLSD_LENGTH);
        while (input.length() >= MLSD_LENGTH && !input.subSequence(0, MLSD_LENGTH).equals(MULTI_LINE_STRING_DELIMITER)) {
            final char ch = input.charAt(0);
            if (ch == '\\') {
                handleEscape(input, output);
            } else {
                handleNormalMultiLineStringCharacter(input, output);
            }
        }
        if (input.length() < MLSD_LENGTH)
            throw new IllegalArgumentException("Multi-line string is not terminated before eof");
        input.delete(0, MLSD_LENGTH);
        return output.toString();
    }

    static void handleNormalMultiLineStringCharacter(StringBuilder input, StringBuilder output) {
        char ch = input.charAt(0);
        if (ch == '\n' || ch == '\r') {
            normalizedNewLines(input, output);
        } else {
            output.append(ch);
            input.deleteCharAt(0);
        }
    }

    /**
     * <p>Convert many subsequent {@code \n} and {@code \r} characters to {@code \n} only. There will be as many {@code
     * \n} characters in the output as many there were in the input and the {@code \r} characters are simply ignored.
     * The only exception is, when there are no {@code \n} characters. In this case there will be one {@code \n} in the
     * output for all the {@code \r} characters.</p>
     *
     * <p>The method deletes the characters from the start of the input {@code input} and append the output
     * to the {@code output}. The original characters will be appended to the end of {@code original} without any
     * conversion.</p>
     *
     * @param input  the input, from which the characters are consumed.
     * @param output where the converted newline(s) are appended to
     */
    private static void normalizedNewLines(StringBuilder input, StringBuilder output) {
        char ch = input.charAt(0);
        int countNewLines = 0;
        while (!input.isEmpty() && (ch == '\n' || ch == '\r')) {
            if (ch == '\n') {
                countNewLines++;
            }
            input.deleteCharAt(0);
            if (!input.isEmpty()) {
                ch = input.charAt(0);
            }
        }
        // if there was a single, or multiple \r without any \n
        if (countNewLines == 0) {
            countNewLines++;
        }
        output.append("\n".repeat(countNewLines));
    }

    private static String getSimpleString(StringBuilder input) {
        final var output = new StringBuilder();
        input.deleteCharAt(0);
        while (!input.isEmpty() && input.charAt(0) != ENCLOSING_CH) {
            final char ch = input.charAt(0);
            if (ch == '\\') {
                handleEscape(input, output);
            } else {
                handleNormalCharacter(input, output);
            }
        }
        if (input.isEmpty()) throw new IllegalArgumentException("String is not terminated before eol");
        input.deleteCharAt(0);
        return output.toString();
    }

    static void handleNormalCharacter(StringBuilder input, StringBuilder output) {
        final char ch = input.charAt(0);
        if (ch == '\n' || ch == '\r')
            throw new IllegalArgumentException(String.format("String not terminated before eol:\n%s...",
                    input.substring(1, Math.min(input.length(), 60))));
        output.append(ch);
        input.deleteCharAt(0);
    }

    private static final String escapes = "btnfr\"'\\";
    private static final String escaped = "\b\t\n\f\r\"'\\";

    /**
     * Handle the escape sequence. The escape sequence is
     *
     * <ul>
     *      <li>backslash and a 'b', 't', 'n', 'f', 'r', '"', '\' or an apostrophe, or</li>
     *      <li>backslash and 2 or 3 octal characters.</li>
     * </ul>
     *
     * @param input  the input string
     * @param output the output string where the escaped character is appended
     */
    static void handleEscape(StringBuilder input, StringBuilder output) {
        input.deleteCharAt(0);
        if (input.isEmpty()) throw new IllegalArgumentException("Source ended inside a string.");
        final var nextCh = input.charAt(0);
        final int esindex = escapes.indexOf(nextCh);
        if (esindex == -1) {
            if (nextCh >= '0' && nextCh <= '3') {
                output.append(octal(input, 3));
            } else if (nextCh >= '4' && nextCh <= '7') {
                output.append(octal(input, 2));
            } else {
                throw new IllegalArgumentException("Invalid escape sequence in string: \\" + nextCh);
            }
        } else {
            output.append(escaped.charAt(esindex));
            input.deleteCharAt(0);

        }
    }

    private static char octal(StringBuilder input, int maxLen) {
        int i = maxLen;
        int occ = 0;
        while (i > 0 && !input.isEmpty() && input.charAt(0) >= '0' && input.charAt(0) <= '7') {
            occ = 8 * occ + input.charAt(0) - '0';
            input.deleteCharAt(0);
            i--;
        }
        return (char) occ;
    }

    @Override
    public Value<StringConstant> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && input.charAt(0) == ENCLOSING_CH) {
            StringBuilder sb = new StringBuilder(input);
            final var word = getString(sb);
            final var rest = sb.toString();
            source.update(rest);
            return new SimpleValue<>(new StringConstant(word));
        }
        return null;
    }
}
