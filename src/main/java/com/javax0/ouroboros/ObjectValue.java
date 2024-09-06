package com.javax0.ouroboros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface ObjectValue extends Value<Map<String,Value<?>>> {
    default Value<?> get(String key) {
        return fields().get(key);
    }

    Map<String, Value<?>> fields();

    class Implementation extends HashMap<String, Value<?>> implements ObjectValue {

        @Override
        public String toString() {
            final var sb = new StringBuilder();
            int i = 0;
            var s = "";
            final var keyList = new ArrayList<>(keySet().stream().toList());
            keyList.sort(String::compareTo);
            for (final var key : keyList) {

                sb.append(s);
                if (sb.length() - i > 40) {
                    sb.append("\n");
                    i = sb.length();
                }
                sb.append('"').append(key).append('"').append(": ");
                final var pad = sb.length();
                sb.append(padTo(pad, key.equals("$") || key.equals("$$") ? key : "" + get(key).get()));
                s = ", ";
            }
            return " {" + sb.toString() + "}";
        }

        private static String padTo(int pad, String s) {
            return s.replace("\n", "\n" + " ".repeat(pad));
        }

        @Override
        public Value<?> get(String key) {
            return super.get(key);
        }

        @Override
        public Map<String, Value<?>> get() {
            return this;
        }

        @Override
        public Value<Map<String,Value<?>>> copy() {
            final var clone = new Implementation();
            for( final var key : keySet() ){
                clone.put(key, get(key).copy());
            }
            return clone;
        }

        @Override
        public Map<String, Value<?>> fields() {
            return this;
        }
    }

}
