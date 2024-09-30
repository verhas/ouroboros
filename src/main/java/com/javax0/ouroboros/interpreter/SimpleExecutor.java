package com.javax0.ouroboros.interpreter;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.constant.*;
import com.javax0.ouroboros.utils.SafeCast;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static java.nio.file.Files.readAllBytes;

public class SimpleExecutor implements Interpreter {
    final ExecutorContext context = new ExecutorContext();

    public SimpleExecutor() {
        ServiceLoader.load(ContextAgent.class).forEach(agent -> agent.register(context, this));
/*
snippet var.program
=H `$program`

This variable references the actual code.
The code is a list of list of blocks `List<List<Block>>`.
Using this variable, you can read and manipulate the code that was not executed yet

end snippet
 */
        context.set("$program", new SimpleValue<>(stack));
    }

    private PrintStream out = System.out;

    public void setOutput(PrintStream out) {
        this.out = out;
    }

    @Override
    public void output(String message) {
        out.print(message);
    }

    private final List<List<Object>> stack = new ArrayList<>();

    private final List<Path> includePath = new ArrayList<>();

    public void includePath(Path path) {
        includePath.add(path);
    }

    @Override
    public void include(String fn) {
        try {
            for (var path : includePath) {
                final var file = path.resolve(fn);
                if (Files.exists(file)) {
                    final var input = new String(readAllBytes(file));
                    final var source = source();
                    source.update(input + source);
                    return;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Source source() {

        final var store = new ArrayList<Interpreter.State>();
        Interpreter.State state;
        while (bottomSource() == null && (state = up()) != null) {
            store.add(state);
        }
        try {
            return bottomSource();
        } finally {
            store.reversed().forEach(this::down);
        }
    }

    private Source bottomSource() {
        purgeStack();

        return Optional.of(stack)
                .filter(it -> !it.isEmpty())
                .map(List::getLast)
                .filter(it -> !it.isEmpty())
                .map(List::getLast)
                .map(SafeCast.to(Source.class)).orElse(null);
    }

    @Override
    public Block pop() {
        purgeStack();
        if (stack.isEmpty() || stack.getLast().isEmpty()) {
            return null;
        }
        final var local = stack.getLast();
        final Object last = local.getLast();
        if (last instanceof Source) {
            final var fetch = getFetcher();
            return Optional.ofNullable(fetch.execute(context)).map(Value::get).orElse(null);
        }
        local.removeLast();
        return AbstractCommand.convertToBLock(last);
    }


    private void purgeStack() {
        if (!stack.isEmpty()) {
            final var local = stack.getLast();
            while (!local.isEmpty() && local.getLast() instanceof Source source && source.get().isEmpty()) {
                local.removeLast();
            }
        }
    }

    @Override
    public void push(Block block) {
        if (stack.isEmpty()) {
            stack.add(new ArrayList<>());
        }
        final var local = stack.getLast();
        local.add(block);
    }

    @Override
    public void pushAll(List<Block> blocks) {
        if (stack.isEmpty()) {
            stack.add(new ArrayList<>());
        }
        final var local = stack.getLast();
        local.addAll(blocks);
    }

    @Override
    public ObjectValue down() {
        stack.add(new ArrayList<>());
        return context.down();
    }

    private record SimpleState(List<Object> blocks, ObjectValue variables) implements State {
    }

    @Override
    public void down(Interpreter.State state) {
        if (state instanceof SimpleState simpleState) {
            stack.add(simpleState.blocks);
            context.down(simpleState.variables);
        } else {
            throw new IllegalArgumentException("State is not a SimpleState: " + state);
        }
    }

    @Override
    public Interpreter.State up() {
        if (stack.isEmpty()) {
            return null;
        }
        return new SimpleState(stack.removeLast(), context.up());
    }

    private int recursiveDepth = 0;

    @Override
    public <T> Value<T> evaluate(Context context, Block block) {
        if (block == null) {
            return null;
        }
        if (block instanceof Command<?> command) {
            try {
                final var maxDepth = context.variable("$maxCallStackSize").map(Value::get).map(SafeCast.to(it -> (Long) it)).orElse(1000L);
                recursiveDepth++;
                if (recursiveDepth > maxDepth) {
                    throw new IllegalArgumentException("Recursion depth exceeded: " + recursiveDepth);
                }
                return (Value<T>) command.execute(context);
            } finally {
                recursiveDepth--;
            }
        } else {
            throw new IllegalArgumentException("Block is not a command: " + block);
        }
    }

    @Override
    public void execute(String input) {
        final var source = new Source(this, input);
        push(source);
        Block result;
        while ((result = pop()) != null) {
            context.set("$it", new SimpleValue<>(result));
            evaluate(context, result);
        }
    }

    private Command<Block> getFetcher() {
        return context.variable("$fetch")
                .map(Value::get)
                .map(SafeCast.to(it -> (Command<Block>) it))
                .orElseThrow(() -> new IllegalArgumentException("No block fetcher"));
    }

}
