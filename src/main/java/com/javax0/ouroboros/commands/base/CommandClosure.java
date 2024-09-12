package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.utils.SafeCast;

/**
 * command_closure
 * {%COMMAND closure%}
 * <p>
 * Creates a new closure from a block.
 * The block is the argument of the command, and in practical cases it has to be quoted.
 * <p>
 * A closure is a block of code that preserves the environment where it was created.
 * If there is a variable defined as `x` in the environment where the closure is created, then this will be available when the closure runs.
 * {%EXAMPLE/simple_closure%}
 * <p>
 * It will print `"Hello, World!"` even though the variable `localVariable` is not defined outside the block.
 * <p>
 * Closures inherit ALL levels of the context they are created and this contex closure will have precedence over the actual context.
 * For example, it is possible to redefine the `puts` command inside a closure and still use the original when the closure runs:
 * <p>
 * {%EXAMPLE/redefine_puts%}
 * <p>
 * When the closure runs, it still uses the `puts` command that was defined in the global context by the time the closure was created.
 * When using the `puts` later, it is already calling the closure.
 * <p>
 * Closures can also be used to create higher order functions.
 * <p>
 * {%EXAMPLE/high_order_func%}
 * <p>
 * In this case the function `greet` creates two closures during the two invocations, and each resulting closure will have it's own value for the variable `z`.
 * <p>
 * Finally, if you find it cumbersome to write `clsoure` and `'` all the time, you can define a symbol for the combination like in the following example.
 * <p>
 * {%EXAMPLE/closure_quote%}
 * end
 *
 * @param <T> the type of the value
 */
public class CommandClosure<T> extends AbstractCommand<Closure<T>> {

    public CommandClosure(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Closure<T>> execute(Context context) {
        final var arg = nextArgument(context).map(SafeCast.to(Block.class))
                .orElseThrow(() -> new IllegalArgumentException("Closure argument missing"));
        final var closure = context.closure();
        return new SimpleValue<>(new Closure<>(interpreter, arg, closure));
    }
}
