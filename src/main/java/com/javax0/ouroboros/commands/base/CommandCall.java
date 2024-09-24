package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.ObjectValue;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.Optional;

/**
 * command_call
 * {%COMMAND call%}
 * Calls a method on an object.
 * <p>
 * The first argument is the object that contains the method.
 * The second argument is the name of the method to be called.
 * <p>
 * During execution, the method can accept additional arguments, similar to a function call.
 * The `this` variable is set to the object that contains the method during its execution.
 * <p>
 * {%EXAMPLE/call%}
 *
 * end
 *
 * @param <T>
 */
public class CommandCall<T> extends AbstractCommand<T> {
    public CommandCall(Interpreter interpreter) {
super(interpreter);
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
            object = Optional.ofNullable(interpreter.evaluate(context, command))
                    .map(Value::get)
                    .map(SafeCast.to(ObjectValue.class))
                    .orElseThrow(() -> new IllegalArgumentException("The first argument of 'call' should be an object"));
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
                    oldThis.ifPresentOrElse(it -> context.set("this", it), () -> context.remove("this"));
                }
            } else if (object instanceof Value<?> value) {
                result = (Value<T>) value;
            } else {
                result = new SimpleValue<>((T) object);
            }
            object = Optional.ofNullable(result)
                    .map(Value::get)
                    .map(SafeCast.to(ObjectValue.class))
                    .orElse(null);
        } while (vararg);
        return result;
    }
}
