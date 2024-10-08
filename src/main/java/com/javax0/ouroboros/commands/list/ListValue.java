package com.javax0.ouroboros.commands.list;

import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.ObjectValue;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListValue<T> extends AbstractList<Value<T>> implements ObjectValue {
    private final ObjectValue fields = new ObjectValue.Implementation();
    private final List<Value<T>> elements = new ArrayList<>();
    private final Interpreter interpreter;

    public ListValue(Interpreter interpreter) {
        this.interpreter = interpreter;
    }


    public List<Value<T>> values() {
        return elements;
    }

    @Override
    public Map<String, Value<?>> fields() {
        return fields.fields();
    }


    @Override
    public Value<T> get(int index) {
        return elements.get(index);
    }

    @Override
    public Value<T> set(int index, Value<T> element) {
        return elements.set(index, element);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        int i = 0;
        var s = "";
        for (final var value : elements) {
            sb.append(s);
            if (sb.length() - i > 40) {
                sb.append("\n");
                i = sb.length();
            }
            sb.append(value.get());
            s = ", ";
        }
        return "[" + sb.toString() + "]";
    }

    @Override
    public Map<String, Value<?>> get() {
        return fields.fields();
    }

    @Override
    public Value<Map<String, Value<?>>> copy() {
        final var clone = new ListValue<T>(interpreter);
        for (final var value : elements) {
            clone.values().add(value.copy());
        }
        return clone;
    }
}
