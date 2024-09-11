package com.javax0.ouroboros;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleBlock implements Block {
    final List<Block> commands;

    public SimpleBlock(List<Block> commands) {
        this.commands = commands;
    }

    @Override
    public List<Block> subBlocks() {
        return commands;
    }

    @Override
    public String toString() {
        return "{" + commands.stream().map(Object::toString).collect(Collectors.joining(" ")) + "}";
    }
}
