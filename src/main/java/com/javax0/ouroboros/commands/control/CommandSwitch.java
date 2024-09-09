package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Block;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.Optional;

/**
 * command_switch
 * {%COMMAND switch%}
 * <p>
 * The switch command must have an even number of parameters till an `{}` or end of the tokens.
 * The switch command must be followed by an even number of blocks.
 * The first, the third and every odd block is evaluated as a condition until one is found `true`.
 * When a condition is true the even block following the condition is evaluated and the result is returned.
 * The rest of the conditions and all subsequent blocks are ignored.
 * <p>
 * {%EXAMPLE/switch1%}
 * <p>
 * The same example can also be written as:
 * <p>
 * {%EXAMPLE/switch2%}
 * <p>
 * end
 *
 * @param <T>
 */
public class CommandSwitch<T> extends AbstractCommand<T> {

    public CommandSwitch(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var blocks = new ArrayList<Block>();
        while (true) {
            final var block = interpreter.pop();
            if (block == null || block.subBlocks().isEmpty()) {
                break;
            }
            blocks.add(block);
        }
        if (blocks.size() % 2 != 0) {
            throw new IllegalArgumentException("The switch command must have an even number of parameters till an `}`");
        }
        for (int i = 0; i < blocks.size(); i += 2) {
            final var condition = Optional.ofNullable(interpreter.evaluate(context, blocks.get(i)))
                    .map(Value::get)
                    .map(this::toBoolean).orElse(false);
            if (condition) {
                return interpreter.evaluate(context, blocks.get(i + 1));
            }
        }
        return null;
    }

}
