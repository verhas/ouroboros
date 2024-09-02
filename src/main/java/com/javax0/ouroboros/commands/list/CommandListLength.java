package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_list_length
 * {%COMMAND list.length%}
 * A list method that returns the length of the list.
 *
 * end
 */

public class CommandListLength extends AbstractCommand<Long> {
    public CommandListLength(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<Long> execute(Context context) {
        final var list = context.variable("this").map(Value::get)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'first"));
        switch(list){
            case ListValue<?> lv -> {
                if( lv.values().isEmpty() ){
                    throw new IllegalArgumentException("The list is empty");
                }
                return new SimpleValue<>( Long.valueOf(lv.values().size()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
