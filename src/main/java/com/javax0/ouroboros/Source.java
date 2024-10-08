package com.javax0.ouroboros;

import com.javax0.ouroboros.commands.constant.Constant;

public class Source extends Constant<String> {
    public Source(final Interpreter interpreter, final String value) {
        super(interpreter);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public Value<String> execute(Context context) {
        return value;
    }

    @Override
    public String toString() {
        return value.get();
    }


    public void update(final String value) {
        ((SimpleValue<String>)this.value).set(value);
    }

}
