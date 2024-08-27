package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Value;

public class StringConstant extends Constant<String> {
    public StringConstant(final String value) {
        this.value = () -> value;
    }

    @Override
    public String toString() {
        return "\"" + value.get() + '"';
    }
}
