package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Value;

public class LongConstant extends Constant<Long> implements NumericConstant {
    public LongConstant(final Long value) {
        this.value = () -> value;
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
