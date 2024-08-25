package com.javax0.ouroboros.commands;

import com.javax0.ouroboros.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand<T> implements Command<T> {

    public Value<T> execute(Context context, List<Block> arguments){
        throw new IllegalArgumentException("Command does not implement execute(...)");
    };

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
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
                    return null; // TODO error handling
                } else {
                    break;
                }
            }
            arguments.add(block);
        }
        return execute(context, arguments);
    }

}
