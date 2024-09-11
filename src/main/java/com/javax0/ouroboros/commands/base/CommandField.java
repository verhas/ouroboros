package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.Optional;

/**
 * command_field
 * {%COMMAND field%}
 * <p>
 * Get an object's field's value.
 * The fist argument is the object, and the second argument is the name of the field.
 * When the first argument is `*` then the command will fetch the subsequent arguments and navigate alon g the path of fields in the object structure.
 * The navigation stops when there are no more fields, or a `{}`.
 * <p>
 * {%EXAMPLE/field%}
 * end
 *
 * @param <T>
 */
public class CommandField<T> extends AbstractCommand<T> {
    public CommandField(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<T> execute(Context context) {
        var objectArg = interpreter.pop();
        final var isopt = isOpt(objectArg);
        final var vararg = isVararg(objectArg);
        if (vararg ||isopt) {
            objectArg = interpreter.pop();
        }


        Object object = Optional.ofNullable(switch (objectArg) {
                    case Command<?> command ->
                            Optional.ofNullable(interpreter.evaluate(context, command)).map(Value::get).orElse(null);
                    case Value<?> value -> value.get();
                    default -> null;
                })
                .map(SafeCast.to(ObjectValue.class))
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'field' should be an object"));
        var done = false;
        do {
            final String name = getName(context).orElse(null);
            if (name != null) {
                final var opobject = Optional.of(object)
                        .map(SafeCast.to(ObjectValue.class))
                        .map(o -> o.get(name))
                        .map(Value::get);
                object = isopt ?
                        opobject.orElse(null) :
                        opobject.orElseThrow(() -> new IllegalArgumentException("Field " + name + " is not found or not found or not an object"));
                done = object == null;
            } else {
                done = true;
            }
        } while (vararg && !done);
        return switch (object) {
            case Value<?> value -> (Value<T>) value;
            case null -> new SimpleValue<>(null);
            default -> new SimpleValue<>((T) object);
        };
    }
}
