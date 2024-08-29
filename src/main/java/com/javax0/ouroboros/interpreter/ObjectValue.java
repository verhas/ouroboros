package com.javax0.ouroboros.interpreter;

import com.javax0.ouroboros.Value;

import java.util.HashMap;

public class ObjectValue extends HashMap<String, Value<?>> {
    @Override
    public String toString() {
        final var sb = new StringBuilder();
        int i = 0;
        var s = "";
        for( final var entry : entrySet() ){
            sb.append(s);
            if( sb.length() -i > 40 ){
                sb.append("\n");
                i = sb.length();
            }
            sb.append('"').append(entry.getKey()).append('"').append(": ");
            final var pad = sb.length();
            sb.append(padTo(pad,""+entry.getValue().get()));
            s = ", ";
        }
        return " {" + sb.toString() + "}";
    }

    private static String padTo(int pad, String s){
        return s.replace("\n", "\n"+ " ".repeat(pad));
    }

}
