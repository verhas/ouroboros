package com.javax0.ouroboros.commands;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class AbstractCommandOp<T> extends AbstractCommand<T> {

    protected Boolean toBoolean(Object value) {
        return switch (value) {
            case Long l -> l != 0;
            case Double v -> !v.equals(0.0);
            case BigInteger bigInteger -> !bigInteger.equals(BigInteger.ZERO);
            case BigDecimal bigDecimal -> !bigDecimal.equals(BigDecimal.ZERO);
            case String s -> !s.isEmpty() && !"0".equals(s) && !"false".equalsIgnoreCase(s);
            case Boolean b -> b;
            case null -> false;
            default ->
                    throw new IllegalArgumentException("Cannot convert " + value + "/" + value.getClass() + " to a boolean");
        };
    }

    protected Long toLong(Object value) {
        return switch (value) {
            case Long l -> l;
            case Double v -> v.longValue();
            case BigInteger bigInteger -> bigInteger.longValue();
            case BigDecimal bigDecimal -> bigDecimal.longValue();
            case Boolean b -> b ? 1L : 0L;
            case String s -> {
                try {
                    yield Long.parseLong(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Cannot convert '" + value + "' to a long");
                }
            }
            case null -> throw new IllegalArgumentException("Cannot convert null to a long");
            default ->
                    throw new IllegalArgumentException("Cannot convert " + value + "/" + value.getClass() + " to a long");
        };
    }

    protected Double toDouble(Object value) {
        return switch (value) {
            case Long l -> l.doubleValue();
            case Double v -> v;
            case BigInteger bigInteger -> bigInteger.doubleValue();
            case BigDecimal bigDecimal -> bigDecimal.doubleValue();
            case Boolean b -> b ? 1.0 : 0.0;
            case String s -> {
                try {
                    yield Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Cannot convert '" + value + "' to a double");
                }
            }
            case null -> throw new IllegalArgumentException("Cannot convert null to a double");
            default ->
                    throw new IllegalArgumentException("Cannot convert " + value + "/" + value.getClass() + " to a double");
        };
    }

    protected BigInteger toBigInteger(Object value) {
        return switch (value) {
            case Long l -> BigInteger.valueOf(l);
            case Double v -> BigInteger.valueOf(v.longValue());
            case BigInteger bigInteger -> bigInteger;
            case BigDecimal bigDecimal -> bigDecimal.toBigInteger();
            case Boolean b -> b ? BigInteger.ONE : BigInteger.ZERO;
            case String s -> {
                try {
                    yield new BigInteger(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Cannot convert '" + value + "' to a BigInteger");
                }
            }
            case null -> throw new IllegalArgumentException("Cannot convert null to a BigInteger");
            default ->
                    throw new IllegalArgumentException("Cannot convert " + value + "/" + value.getClass() + " to a BigInteger");
        };
    }

    protected BigDecimal toBigDecimal(Object value) {
        return switch (value) {
            case Long l -> BigDecimal.valueOf(l);
            case Double v -> BigDecimal.valueOf(v);
            case BigInteger bigInteger -> new BigDecimal(bigInteger);
            case BigDecimal bigDecimal -> bigDecimal;
            case Boolean b -> b ? BigDecimal.ONE : BigDecimal.ZERO;
            case String s -> {
                try {
                    yield new BigDecimal(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Cannot convert '" + value + "' to a BigDecimal");
                }
            }
            case null -> throw new IllegalArgumentException("Cannot convert null to a BigDecimal");
            default ->
                    throw new IllegalArgumentException("Cannot convert " + value + "/" + value.getClass() + " to a BigDecimal");
        };
    }

    protected String toString(Object value) {
        return switch (value) {
            case Long l -> Long.toString(l);
            case Double v -> Double.toString(v);
            case BigInteger bigInteger -> bigInteger.toString();
            case BigDecimal bigDecimal -> bigDecimal.toString();
            case Boolean b -> Boolean.toString(b);
            case String s -> s;
            case null, default -> "" + value;
        };
    }

}
