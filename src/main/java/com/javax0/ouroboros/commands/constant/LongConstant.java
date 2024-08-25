package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Value;

public class LongConstant extends Constant<Long> {
    public LongConstant(final Long value) {
        this.value = new Value<>() {
            @Override
            public Long get() {
                return value;
            }
        };
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
