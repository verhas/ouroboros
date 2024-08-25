package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.AbstractCommand.Arguments;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.List;
import java.util.Map;

@Arguments(min = 1, max = 2)
public class CommandObject extends AbstractCommand<Void> {
    public CommandObject(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context, List<Block> arguments) {
        final var nameArg = arguments.getFirst();
        final String name;
        if (nameArg instanceof Value<?> nameValue) {
            name = nameValue.get().toString();
        } else {
            throw new IllegalArgumentException("The first argument of 'set' should be a name");
        }
        final var object = new ObjectValue();
        if (arguments.size() == 2) {
            final var parent = interpreter.<Map<String, Value<?>>>evaluate(context, arguments.getLast());
            if (parent != null) {
                if (parent instanceof ObjectValue parentObject) {
                    object.get().putAll(parentObject.get());
                } else {
                    throw new IllegalArgumentException("The second argument of 'object' should be a parent object");
                }
            }
        }
        context.set(name, new SimpleValue<>(object));
        return null;
    }
}