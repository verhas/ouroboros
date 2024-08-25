package com.javax0.ouroboros.commands.base;

import com.javax0.ouroboros.Command;
import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;

public class BareWord<T> extends AbstractCommand<T> implements Value<String>{
    private final String word;

    public BareWord(final String value) {
        this.word = value;
    }

    @Override
    public String get() {
        return word;
    }

    @Override
    public Value<T> execute(Context context) {
        final var object = context.variable(word).map(Value::get).orElse(null);
        if (object instanceof Command<?> command) {
            return (Value<T>) command.execute(context);
        }else if( object instanceof Value<?> value){
            return (Value<T>) value;
        }else{
            return new SimpleValue<>((T)object);
        }
    }

    @Override
    public String toString() {
        return word;
    }
}
