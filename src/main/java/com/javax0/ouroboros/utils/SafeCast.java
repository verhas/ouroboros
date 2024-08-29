package com.javax0.ouroboros.utils;

import java.util.function.Function;

/**
 * Utility class to cast objects to a specific class.
 * It can be used on Optionals and in streams.
 * Instead of filtering and then mapping the object to the desired class
 * the {@link #to(Class)} method can be used to cast the object to the
 * desired class.
 */
public class SafeCast {
    public static <T> Function<Object,T> to(final Class<T> klass) {
        return (Object object) -> {
            if (klass.isAssignableFrom(object.getClass())) {
                return klass.cast(object);
            }
            return null;
        };
    }
    public static <T> Function<Object,T> to(final Function<Object,T> f) {
        return (Object object) -> {
            try{
                return f.apply(object);
            }catch (ClassCastException e){
                return null;
            }
        };
    }
}
