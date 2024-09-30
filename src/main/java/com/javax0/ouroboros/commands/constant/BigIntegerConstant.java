package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;

import java.math.BigInteger;

public class BigIntegerConstant extends Constant<BigInteger> implements NumericConstant {
    public BigIntegerConstant(final BigInteger value) {
        super(null);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
