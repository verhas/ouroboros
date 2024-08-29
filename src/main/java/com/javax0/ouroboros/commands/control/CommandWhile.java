package com.javax0.ouroboros.commands.control;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;


public class CommandWhile<T> extends AbstractCommand<T> {

    public CommandWhile(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        final var conditionBlock = interpreter.pop();
        var condition = toBoolean(interpreter.evaluate(context, conditionBlock).get());
        final var block = interpreter.pop();
        Value<T> result = null;
        while (condition) {
            result = block == null ? null : interpreter.evaluate(context, block);
            condition = toBoolean(interpreter.evaluate(context, conditionBlock).get());
        }
        return result;
    }



}
