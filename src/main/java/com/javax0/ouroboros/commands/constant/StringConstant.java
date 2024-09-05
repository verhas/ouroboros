package com.javax0.ouroboros.commands.constant;

import com.javax0.ouroboros.SimpleValue;
/**
 * command_string_constant
 * {%COMMAND string_constant%}
 * A string value.
 * If a string literal is present in the code, the lexical analyzers will create a string constant from it.
 * The string constant is a constant that holds a string value.
 * end
 *
 */
public class StringConstant extends Constant<String> {
    public StringConstant(final String value) {
        this.value = new SimpleValue<>(value);
    }

    @Override
    public String toString() {
        return "\"" + value.get() + '"';
    }
}
