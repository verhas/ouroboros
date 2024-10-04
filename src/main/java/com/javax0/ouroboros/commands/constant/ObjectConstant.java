package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;

import java.math.BigDecimal;

public class ObjectConstant extends Constant<Object> implements NumericConstant {
    public ObjectConstant(final Object value) {
        super(null);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}