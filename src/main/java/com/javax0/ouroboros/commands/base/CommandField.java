package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.Optional;

public class CommandField<T> extends AbstractCommand<T> {
    public CommandField(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        var objectArg = interpreter.pop();
        final var vararg = isVararg(objectArg);
        if (vararg) {
            objectArg = interpreter.pop();
        }

        Object object = Optional.ofNullable(switch (objectArg) {
                    case Command<?> command -> Optional.ofNullable(interpreter.evaluate(context, command)).map(Value::get).orElse(null);
                    case Value<?> value -> value.get();
                    default -> null;
                })
                .map(SafeCast.to(ObjectValue.class))
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'field' should be an object"));
        var done = false;
        do {
            final String name = getName(context).orElse(null);
            if (name != null) {
                object = Optional.of(object)
                        .map(SafeCast.to(ObjectValue.class))
                        .map(o -> o.get(name))
                        .map(Value::get)
                        .orElseThrow(() -> new IllegalArgumentException("Field " + name + " is not found or not found or not an object"));
            } else {
                done = true;
            }
        } while (vararg && !done);
        return switch (object) {
            case Value<?> value -> (Value<T>) value;
            default -> new SimpleValue<>((T) object);
        };
    }
}
