package com.javax0.ouroboros;

import java.util.List;

/**
 * The compiled code for a block.
 */
public interface Block {
    List<Block> subBlocks();
}
