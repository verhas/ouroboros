package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.interpreter.ObjectValue;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.Optional;

/**snippet command_setf
 * {%COMMAND setf%}
 * Set a field in an object.
 * The first argument is the object, the second argument is the name of the field, and the third argument is the value.
 * end snippet
 */
public class CommandSetf extends AbstractCommand<Void> {
    public CommandSetf(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Void> execute(Context context) {
        final ObjectValue object = Optional.ofNullable(interpreter.pop())
                .map(SafeCast.to(Command.class))
                .map(cmd -> interpreter.evaluate(context, cmd))
                .map(Value::get)
                .map(SafeCast.to(ObjectValue.class))
                .orElseThrow(() -> new IllegalArgumentException("The first argument of 'setf' should be an object"));
        final var nameArg = interpreter.pop();
        final String name = Optional.ofNullable(nameArg)
                .map(SafeCast.to(Value.class))
                .map(Value::get)
                .map(Object::toString)
                .orElseGet(()
                        -> Optional.ofNullable(nameArg)
                        .map(SafeCast.to(Command.class))
                        .map(cmd -> interpreter.evaluate(context, cmd))
                        .map(Value::get)
                        .map(Object::toString)
                        .orElseThrow(() -> new IllegalArgumentException("The second argument of 'setf' should be a name")));

        final var value = interpreter.evaluate(context, interpreter.pop());
        object.fields().put(name, value);
        return null;
    }


}
