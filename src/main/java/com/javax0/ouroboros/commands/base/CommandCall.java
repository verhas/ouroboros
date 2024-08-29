package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;

import java.util.Optional;


public class CommandCall<T> extends AbstractCommand<T> {
    public CommandCall(Interpreter interpreter) {
        set(interpreter);
    }

    /**
     * Call a method.
     * <p>
     * The first argument is the object that has the method.
     *
     * @param context the context
     * @return the result of the method call
     */
    @Override
    public Value<T> execute(Context context) {
        var objectArg = interpreter.pop();
        final var vararg = isVararg(objectArg);
        if (vararg) {
            objectArg = interpreter.pop();
        }
        ObjectValue object;
        if (objectArg instanceof Command<?> command) {
            final var obj = interpreter.evaluate(context, command).get();
            if (obj instanceof ObjectValue objectValue) {
                object = objectValue;
            } else {
                throw new IllegalArgumentException("The second argument of 'set' should be an object");
            }
        } else {
            throw new IllegalArgumentException("The second argument of 'set' should be an object");
        }

        Value<T> result = null;
        boolean first = true;
        do {
            final String name = getName(context).orElse(null);
            if (name == null && first) {
                throw new IllegalArgumentException("Method name is missing");
            }
            first = false;
            if (name == null) {
                return result;
            }
            if (object == null) {
                throw new IllegalArgumentException("Method " + name + " is called on a non object");
            }
            final var fun = Optional.ofNullable(object.get(name))
                    .map(Value::get)
                    .orElseThrow(() -> new IllegalArgumentException("Method " + name + " is not found"));

            if (fun instanceof Command<?> funCmd) {
                final var oldThis = context.variable("this");
                try {
                    context.set("this", new SimpleValue<>(object));
                    result = (Value<T>) funCmd.execute(context);
                } finally {
                    if (oldThis.isPresent()) {
                        context.set("this", oldThis.get());
                    } else {
                        context.remove("this");
                    }
                }
            } else if (object instanceof Value<?> value) {
                result = (Value<T>) value;
            } else {
                result = new SimpleValue<>((T) object);
            }
            if (result != null && result.get() instanceof ObjectValue objectValue) {
                object = objectValue;
            } else {
                object = null;
            }
        } while (vararg);
        return result;
    }
}
