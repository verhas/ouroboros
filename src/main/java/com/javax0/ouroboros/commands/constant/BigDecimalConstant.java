package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;

import java.math.BigDecimal;

public class BigDecimalConstant extends Constant<BigDecimal> implements NumericConstant {
    public BigDecimalConstant(final BigDecimal value) {
        super(null);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
