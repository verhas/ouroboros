package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Value;

public class DoubleConstant extends Constant<Double> implements NumericConstant {
    public DoubleConstant(final Double value) {
        this.value = () -> value;
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
