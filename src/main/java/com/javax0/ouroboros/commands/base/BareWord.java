package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

/** command_bare_word
 * {%COMMAND BareWord%}
 * <p>
 * Bare word is an internal command that is used to execute a bare word.
 * You will not use this command in the language.
 * This is the command created by the lexical analysis when a bare word is found.
 * In turn, then this command will look up the actual command associated with that bare word at the time and location of the execution.
 * It will consult the context and the variables and execute the command that is found.
 * <p>
 * end
 *
 * @param <T>
 */
public class BareWord<T> extends AbstractCommand<T> implements Value<String> {
    private final String word;

    public BareWord(Interpreter interpreter, final String value) {
super(interpreter);
        this.word = value;
    }

    @Override
    public String get() {
        return word;
    }

    @Override
    public Value<String> copy() {
        return new BareWord<>(interpreter, word);
    }

    @Override
    public Value<T> execute(Context context) {
        final var object = context.variable(word).orElseThrow(() -> new IllegalArgumentException("Variable " + word + " is not defined")).get();
        return switch (object) {
            case Command<?> command -> (Value<T>) command.execute(context);
            case Value<?> value -> (Value<T>) new SimpleValue<>(value);
            case null ->  (Value<T>) new SimpleValue<>(null);
            default -> new SimpleValue<>((T) object);
        };
    }

    @Override
    public String toString() {
        return word;
    }
}
