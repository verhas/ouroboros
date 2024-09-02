package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

/**
 * command_list_rest
 * {%COMMAND list.rest%}
 * A list method that returns a new list that contains all the elements of the original list except the first one.
 * If the list is empty, then the command throws an exception.
 * The command is defined on the list object.
 * The command does not have any argument.
 * end
 * @param <T>
 */
public class CommandListRest<T> extends AbstractCommand<ListValue<T>> {
    public CommandListRest(Interpreter interpreter) {
        set(interpreter);
    }

    @Override
    public Value<ListValue<T>> execute(Context context) {
        final var list = context.variable("this").map(Value::get)
                .orElseThrow(() -> new IllegalArgumentException("There is no argument to the command 'first"));
        switch(list){
            case ListValue<?> lv -> {
                if( lv.values().isEmpty() ){
                    throw new IllegalArgumentException("The list is empty");
                }
                final var newList = new ListValue<T>(interpreter);
                for( int i = 1; i < lv.values().size(); i++ ){
                    newList.add((Value<T>) lv.values().get(i));
                }
                return new SimpleValue<>(newList);
            }
            default -> throw new IllegalStateException("Unexpected value: " + list);
        }
    }
}
