package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.stream.Collectors;

/**
 * command_block
 * {%COMMAND Block%}
 * This command is used to execute a block.
 * A block is not represented by a bareword; rather, it is represented by a `{` and `}` pair.
 * The block is a sequence of commands that are executed one after the other.
 * The block can contain other blocks.
 * <p>
 * The block is executed in a new context.
 * Variables defined within the block are not visible outside the block.
 * However, the block can access variables defined outside of it.
 * The special variable `$` is set to the value of the context object when the block is executed.
 * The special variable `$$` is set to the variables defined in the context object of the surrounding block.
 * <p>
 * The context object is an object that has the local variables as fields.
 * <p>
 * {%EXAMPLE/block%}
 * end
 *
 * @param <T>
 */
public class CommandBlock<T> extends AbstractCommand<T> {

    private final Block block;

    public Block getBlock() {
        return block;
    }

    public CommandBlock(Interpreter interpreter, Block block) {
        this.interpreter = interpreter;
        this.block = block;
    }

    @Override
    public Value<T> execute(Context context) {
        try {
            final var variables = interpreter.down();
            context.set("$$", new SimpleValue<>(variables));
            context.set("$", new SimpleValue<>(context.bottom()));
            Value<T> result = null;
            interpreter.pushAll(block.subBlocks().reversed());
            Block block;
            while ((block = interpreter.pop()) != null) {
                result = interpreter.evaluate(context, block);
            }
            return result;
        } finally {
            interpreter.up();
        }
    }

    @Override
    public String toString() {
        return "{" +
                block.subBlocks().stream().map(Object::toString).collect(Collectors.joining(" ")) +
                "}";
    }
}
