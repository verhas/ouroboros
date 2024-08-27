package com.javax0.ouroboros.interpreter;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ExecutorContext implements Context {
    private final List<ObjectValue> stack = new ArrayList<>();

    @Override
    public <T> Optional<Value<T>> variable(String name) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            final var variables = stack.get(i);
            if (variables.containsKey(name)) {
                return Optional.of((Value<T>) variables.get(name));
            }
        }
        return Optional.empty();
    }

    @Override
    public ObjectValue bottom() {
        return stack.getLast();
    }

    @Override
    public <T> void set(String name, Value<T> value) {
        if (stack.isEmpty()) {
            stack.add(new ObjectValue());
        }
        final var variables = stack.getLast();
        variables.put(name, value);
    }

    @Override
    public void remove(String name) {
        if (stack.isEmpty()) {
            stack.add(new ObjectValue());
        }
        final var variables = stack.getLast();
        variables.remove(name);
    }

    @Override
    public <T> void setg(String name, Value<T> value) {
        if (stack.isEmpty()) {
            stack.add(new ObjectValue());
        }
        final var variables = stack.getFirst();
        variables.put(name, value);
    }

    ObjectValue up() {
        return stack.removeLast();
    }

    ObjectValue down() {
        final var variables = stack.getLast();
        stack.add(new ObjectValue());
        return variables;
    }

    void down(ObjectValue variables) {
        stack.add(variables);
    }

}