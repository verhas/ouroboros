package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;

public class LongConstant extends Constant<Long> implements NumericConstant {
    public LongConstant(final Long value) {
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
