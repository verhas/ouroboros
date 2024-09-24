package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;

/**
 * command_double_constant
 * {%COMMAND double_constant%}
 * A number that conforms to the Java double type.
 * If a floating number literal is present in the code, the lexical analyzers will create a double constant from it.
 * The double constant is a constant that holds a double value.
 * end
 *
 */
public class DoubleConstant extends Constant<Double> implements NumericConstant {
    public DoubleConstant( final Double value) {
        super(null);
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return value.get()+"";
    }
}
