package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Block;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.Optional;

/**
 * command_while
 * {%COMMAND while%}
 * <p>
 * The first argument is the condition and the second argument is the block.
 * The command executes the two blocks one after the other while the result of the first block is true.
 * end
 *
 * @param <T>
 */
public class CommandWhile<T> extends AbstractCommand<T> {

    public CommandWhile(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var conditionBlock = interpreter.pop();
        final var block = interpreter.pop();

        var condition = evaluateCondition(context, conditionBlock);
        Value<T> result = null;
        while (condition) {
            result = block == null ? null : interpreter.evaluate(context, block);
            condition = evaluateCondition(context, conditionBlock);
        }
        return result;
    }

    private Boolean evaluateCondition(Context context, Block conditionBlock) {
        return Optional.ofNullable(conditionBlock)
                .map(it -> interpreter.evaluate(context, it))
                .map(Value::get)
                .map(this::toBoolean)
                .orElse(false);
    }


}
