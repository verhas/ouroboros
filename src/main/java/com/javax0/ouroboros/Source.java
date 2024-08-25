package com.javax0.ouroboros;

import com.javax0.ouroboros.commands.constant.Constant;

public class Source extends Constant<String> {
    public Source(final Interpreter interpreter, final String value) {
        set(interpreter);
        this.value = new MutableValue<>(value);
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
        ((MutableValue<String>)this.value).set(value);
    }
}
