package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Block;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.List;

@AbstractCommand.Arguments(2)
public class CommandAdd extends AbstractCommand<Long> {

    public CommandAdd(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Long> execute(Context context, List<Block> arguments) {
        final var left = (Long)interpreter.evaluate(context, arguments.getFirst()).get();
        final var right = (Long)interpreter.evaluate(context, arguments.getLast()).get();
        return new SimpleValue<>(left + right);
    }

    @Override
    public String toString() {
        return "CommandAdd";
    }
}
