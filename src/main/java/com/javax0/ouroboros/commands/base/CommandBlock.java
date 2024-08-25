package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;

import java.util.stream.Collectors;

public class CommandBlock<T> extends AbstractCommand<T> {

    private final Block block;

    public CommandBlock(Interpreter interpreter, Block block) {
        this.interpreter = interpreter;
        this.block = block;
    }

    @Override
    public Value<T> execute(Context context) {
        try {
            interpreter.down();
            Value<T> result = null;
            interpreter.pushAll(block.subBlocks().reversed());
            Block block;
            while( (block = interpreter.pop()) != null ){
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
