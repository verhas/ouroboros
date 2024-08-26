package com.javax0.ouroboros.commands;

import com.javax0.ouroboros.*;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand<T> implements Command<T> {

    public Value<T> execute(Context context, List<Block> arguments){
        throw new IllegalArgumentException("Command does not implement execute(...)");
    };

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @Inherited
    public @interface Arguments {
        int value() default -1;

        int min() default -1;

        int max() default -1;
    }

    protected Interpreter interpreter;

    @Override
    public void set(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    protected int getNumberOfArguments() {
        final var value = this.getClass().getAnnotation(Arguments.class).value();
        final var max = this.getClass().getAnnotation(Arguments.class).max();
        if (max != -1) {
            return max;
        }
        return value;
    }

    protected int getMinNumberOfArguments() {
        final var value = this.getClass().getAnnotation(Arguments.class).value();
        final var min = this.getClass().getAnnotation(Arguments.class).min();
        if (min != -1) {
            return min;
        }
        return value;
    }

    @Override
    public Value<T> execute(Context context) {
        final var min = getMinNumberOfArguments();
        final var max = getNumberOfArguments();
        final var arguments = new ArrayList<Block>();
        for (int i = 0; i < max; i++) {
            final var block = interpreter.pop();
            if (block == null) {
                if (i < min) {
                    throw new IllegalArgumentException("Not enough arguments for command " + this);
                } else {
                    break;
                }
            }
            arguments.add(block);
        }
        return execute(context, arguments);
    }

    /**
     * Get the argument from the interpreter stack. The argument can be a command or a value. If it is a command then
     * execute it and return the result. If it is a value, then return the value.
     *
     * @param context the context used to execute the command
     * @return the value of the argument
     * @param <K> the type of the argument
     */
    protected <K> Value<K> getArg(Context context) {
        final var object = interpreter.pop();
        return eval(context, object);
    }

    protected  <K> Value<K> eval(Context context, Block object) {
        if (object instanceof Command<?> command) {
            return (Value<K>) command.execute(context);
        } else if (object instanceof Value<?> val) {
            return (Value<K>) val;
        } else {
            return new SimpleValue<>((K) object);
        }
    }

}
