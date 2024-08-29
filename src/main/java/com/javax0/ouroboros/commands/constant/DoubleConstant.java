package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;

public class DoubleConstant extends Constant<Double> implements NumericConstant {
    public DoubleConstant(final Double value) {
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
