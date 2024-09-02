package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Block;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;

/**
 * command_fixup
 * {%COMMAND fixup%}
 * <p>
 * Fix up the source converting all remaining characters of the current source to tokens.
 * After this command is executed, the lexical analyzer changes are not applied to the source anymore.
 * The actual execution of the code is not affected by the command.
 * This command can be used to signal that the part of the code redefining the syntax is over.
 * Other implementations may override the default implementation of this command and do something else, like converting the remaining tokens to some target language.
 * <p>
 * {%EXAMPLE/fixup%}
 * <p>
 * {%EXAMPLE/nofixup%}
 * <p>
 * {%EXPLANATION/fixup_explanation%}
 * end
 */
public class CommandFixup extends AbstractCommand<Void> {

    public CommandFixup(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final var tokens = new ArrayList<Block>();
        while (true) {
            final var token = interpreter.pop();
            if (token == null) {
                break;
            }
            tokens.add(token);
        }
        for (int i = tokens.size() - 1; i >= 0; i--) {
            interpreter.push(tokens.get(i));
        }
        return null;
    }
}
