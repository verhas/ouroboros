package com.javax0.ouroboros;

import java.util.Objects;
import java.util.Optional;

public class DebugUtils {

    public static String hierarchy(Context context, String name) {
        var sb = new StringBuilder();
        var obj = context.variable(name).map(Value::get).orElse(null);
        int i = 1;
        while (obj != null) {
            var s = Objects.toIdentityString(obj);
            s = s.substring(s.indexOf('@'));
            sb.append(i++).append("(").append(s).append(": ").append(obj).append("\n");
            obj = Optional.ofNullable(((ObjectValue) obj).get("$$")).map(Value::get).orElse(null);
        }
        return sb.toString();
    }

}
