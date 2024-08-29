package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.CommandBlock;

import java.util.Map;

public class CommandList<T> extends AbstractCommand<ListValue<T>> {

    public CommandList(Interpreter interpreter) {
        set(interpreter);
    }


    @Override
    public Value<ListValue<T>> execute(Context context) {
        final var list = new ListValue<T>(interpreter);
        final var values = interpreter.pop();
        if (values != null) {
            switch (values) {
                case CommandBlock<?> block -> {
                    for (final var subBlock : block.getBlock().subBlocks()) {
                        final var calculatedValue = interpreter.<Map<String, Value<?>>>evaluate(context, subBlock);
                        list.values().add((Value<T>) calculatedValue);
                    }
                }
                case Command<?> command -> {
                    final var calculatedValue = interpreter.<Map<String, Value<?>>>evaluate(context, command);
                    if (calculatedValue instanceof ListValue<?> lv) {
                        list.values().addAll(((ListValue<T>) lv).values());
                    } else {
                        list.values().add((Value<T>) calculatedValue);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + values);
            }
        }
        return new SimpleValue<>(list);
    }
}
