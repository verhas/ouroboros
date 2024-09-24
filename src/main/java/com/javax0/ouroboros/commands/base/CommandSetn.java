package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.Optional;

/**
 * command_setn
 * {%COMMAND setn%}
 * Alter the value of a variable.
 * <p>
 * The first argument is the name of the variable, and the second argument is the value.
 * <p>
 * The difference between this command and set is that this command will first search for the variable.
 * If there is already a variable with the given name, it will alter the value of that variable instead of creating a
 * new one shadowing it in the local block.
 * <p>
 * {%EXAMPLE/setn1%}
 * <p>
 * {%EXPLANATION/setn1_explanation%}
 * end
 */
public class CommandSetn extends AbstractCommand<Object> {
    public CommandSetn(Interpreter interpreter) {
super(interpreter);
    }

    @Override
    public Value<Object> execute(Context context) {
        final String name = getName(context)
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'set' should be a name"));
        final var value = Optional.ofNullable(interpreter.evaluate(context, interpreter.pop())).orElse(new SimpleValue<>(null));
        final var opt = context.variable(name);
        if( opt.isPresent() ){
            opt.get().set(value.get());
        } else {
            context.set(name, value);
        }
        return value;
    }


}
