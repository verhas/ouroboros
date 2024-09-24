package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;
/**
 * command_long_constant
 * {%COMMAND long_constant%}
 * A number that conforms to the Java long type.
 * If an integer number literal is present in the code, the lexical analyzers will create a long constant from it.
 * The long constant is a constant that holds a long value.
 * end
 *
 */
public class LongConstant extends Constant<Long> implements NumericConstant {
    public LongConstant(final Long value) {
        super(null);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get() + "L";
    }
}
