package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.BlockFetch;

public class BlockFetchRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        context.<Command<?>>set("$fetch",new SimpleValue<>(new BlockFetch(interpreter)));
    }
}
