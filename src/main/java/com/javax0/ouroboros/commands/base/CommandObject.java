package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.Map;

public class CommandObject extends AbstractCommand<ObjectValue> {
    public CommandObject(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<ObjectValue> execute(Context context) {
        final var object = new ObjectValue();
        final var inheritFrom = interpreter.pop();
        if (inheritFrom != null) {
            final var parent = interpreter.<Map<String, Value<?>>>evaluate(context, inheritFrom);
            if (parent != null) {
                if (parent instanceof ObjectValue parentObject) {
                    object.putAll(parentObject);
                } else {
                    throw new IllegalArgumentException("The second argument of 'object' should be a parent object");
                }
            }
        }
        return new SimpleValue<>(object);
    }
}