package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.List;

/**
 * Command to print the value of the top of the stack.
 */
@AbstractCommand.Arguments(1)
public class CommandPuts extends AbstractCommand<String> {

    public CommandPuts(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<String> execute(Context context,List<Block> arguments) {
        final var value = interpreter.evaluate(context, arguments.getFirst());
        String result = value.get().toString();
        interpreter.output(result);
        return new SimpleValue<>(result)  ;
    }

    @Override
    public String toString() {
        return "CommandPuts";
    }
}
