package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.ObjectValue;
import com.javax0.ouroboros.utils.SafeCast;

import java.util.Optional;

/**
 * command_setf
 * {%COMMAND setf%}
 * Set a field in an object.
 * The first argument is the object, the second argument is the name of the field, and the third argument is the value.
 * <p>
 * Example:
 * <p>
 * {%sample/setf1%}
 * <p>
 * will result in
 * <p>
 * {%output%}
 * <p>
 * Note also that the symbols `$` is always available and points to the object that contains the local variables.
 * Similarly, the `pass:[$$]` symbol is also available except the top scope, and it points to the object that contains the local variables of the one level above.
 * You can also reach `pass:[$$]` as a field via `pass:[$$]` traversing the call hierarchy.
 * <p>
 * The next example sets the local variable `A` and after that it modifies it as a field of `$`.
 *
 * <p>
 * {%sample/setf2%}
 * <p>
 * will result in
 * <p>
 * {%output%}
 * <p>
 * The next example uses the `pass:[$$]` to set a field in the object that contains the local variables of the caller.
 * <p>
 * {%sample/setf3%}
 * <p>
 * will result in
 * <p>
 * {%output%}
 * <p>
 * The last example shows that you can assign a function to a field to act as a method.
 * <p>
 * {%sample/setf4%}
 * <p>
 * will result in
 * <p>
 * {%output%}
 * end
 */
public class CommandSetf extends AbstractCommand<Object> {
    public CommandSetf(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Object> execute(Context context) {
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
        return value;
    }


}
