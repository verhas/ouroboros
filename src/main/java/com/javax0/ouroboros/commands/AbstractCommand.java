package com.javax0.ouroboros.commands;

import com.javax0.ouroboros.*;

public abstract class AbstractCommand<T> implements Command<T> {


    protected Interpreter interpreter;

    @Override
    public void set(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Get the argument from the interpreter stack. The argument can be a command or a value. If it is a command then
     * execute it and return the result. If it is a value, then return the value.
     *
     * @param context the context used to execute the command
     * @param <K>     the type of the argument
     * @return the value of the argument
     */
    protected <K> Value<K> getArg(Context context) {
        final var object = interpreter.pop();
        return eval(context, object);
    }

    protected <K> Value<K> eval(Context context, Block object) {
        if (object instanceof Command<?> command) {
            return (Value<K>) command.execute(context);
        } else if (object instanceof Value<?> val) {
            return (Value<K>) val;
        } else {
            return new SimpleValue<>((K) object);
        }
    }

}
