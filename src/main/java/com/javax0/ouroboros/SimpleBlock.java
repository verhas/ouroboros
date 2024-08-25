package com.javax0.ouroboros;

import java.util.List;

public class SimpleBlock implements Block {
    final List<Block> commands;

    public SimpleBlock(List<Block> commands) {
        this.commands = commands;
    }

    @Override
    public List<Block> subBlocks() {
        return commands;
    }
}
