package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Block;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.Optional;


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
