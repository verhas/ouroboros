package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.Map;

/**
 * snippet command_object
 * {%COMMAND object%}
 *
 * Create a new object.
 * The argument is the parent object.
 * The new object will inherit all the fields from the parent object.
 * The inheritance happens using shallow copy.
 *
 * If the arument is `{}` then the new object will be empty at the creation.
 * end snippet
 */
public class CommandObject extends AbstractCommand<ObjectValue> {
    public CommandObject(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<ObjectValue> execute(Context context) {
        final ObjectValue object = new ObjectValue.Implementation();
        final var inheritFrom = interpreter.pop();
        if (inheritFrom != null) {
            final var parent = interpreter.<Map<String, Value<?>>>evaluate(context, inheritFrom);
            if (parent != null) {
                if (parent instanceof ObjectValue parentObject) {
                    object.fields().putAll(parentObject.fields());
                } else {
                    throw new IllegalArgumentException("The second argument of 'object' should be a parent object");
                }
            }
        }
        return new SimpleValue<>(object);
    }
}