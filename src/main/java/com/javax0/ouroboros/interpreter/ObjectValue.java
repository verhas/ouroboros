package com.javax0.ouroboros.interpreter;

import com.javax0.ouroboros.Value;

import java.util.HashMap;
import java.util.Map;

public class ObjectValue {
    private final Map<String, Value<?>> fields = new HashMap<>();

    public Map<String, Value<?>> get() {
        return fields;
    }
}
