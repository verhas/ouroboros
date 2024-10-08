package com.javax0.ouroboros.commands;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.base.BareWord;
import com.javax0.ouroboros.commands.constant.*;
import com.javax0.ouroboros.commands.lexers.NumericLexer;
import com.javax0.ouroboros.utils.SafeCast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractCommand<T> implements Command<T> {


    final protected Interpreter interpreter;

    protected AbstractCommand(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    protected boolean isVararg(Block block) {
        return block instanceof BareWord<?> bw && bw.get() != null && bw.get().contains("*");
    }
    protected boolean isOpt(Block block) {
        return block instanceof BareWord<?> bw && bw.get() != null && bw.get().contains("?");
    }

    protected Optional<String> getName(Context context) {
        final var name = interpreter.pop();
        return Optional.ofNullable(Optional.ofNullable(name)
                .map(SafeCast.to(Value.class))
                .map(Value::get)
                .map(Object::toString)
                .orElseGet(() ->
                        Optional.ofNullable(name)
                                .map(SafeCast.to(Command.class))
                                .map(cmd -> interpreter.evaluate(context, cmd))
                                .map(Value::get)
                                .map(Object::toString)
                                .orElse(null)));
    }

    /**
     * Get the argument from the interpreter stack. The argument can be a command or a value. If it is a command then
     * execute it and return the result. If it is a value, then return the value.
     *
     * @param context the context used to execute the command
     * @param <Q>     the type of the argument
     * @return the value of the argument
     */
    protected <Q> Optional<Q> nextArgument(Context context, Function<Object, Q> converter) {
        return Optional.ofNullable(interpreter.evaluate(context, interpreter.pop()))
                .map(Value::get)
                .map(converter);
    }

    protected <Q> Optional<Q> nextArgument(Context context, Block pop, Function<Object, Q> converter) {
        return Optional.ofNullable(interpreter.evaluate(context, pop))
                .map(Value::get)
                .map(converter);
    }

    protected <Q> Optional<Q> nextArgument(Context context) {
        final var object = interpreter.pop();
        if (object instanceof Command<?>) {
            return Optional.ofNullable(interpreter.<T>evaluate(context, object))
                    .map(r -> (Q) r.get());
        } else if (object instanceof Value<?>) {
            return Optional.of((Q) object);
        } else {
            return Optional.of((Q) new SimpleValue<>(object));
        }
    }

    protected Boolean toBoolean(Object value) {
        return convertToBoolean(value);
    }
    public static Boolean convertToBoolean(Object value) {
        return switch (value) {
            case Integer i -> i != 0;
            case Long l -> l != 0;
            case Double v -> !v.equals(0.0);
            case BigInteger bigInteger -> !bigInteger.equals(BigInteger.ZERO);
            case BigDecimal bigDecimal -> !bigDecimal.equals(BigDecimal.ZERO);
            case String s -> !s.isEmpty() && !"0".equals(s) && !"false".equalsIgnoreCase(s);
            case Boolean b -> b;
            case null -> false;
            default -> true;
        };
    }


    protected Long toLong(Object value) {
        return convertToLong(value);
    }
    public static Long convertToLong(Object value) {
        return switch (value) {
            case Integer i -> (long) i;
            case Long l -> l;
            case Double v -> v.longValue();
            case BigInteger bigInteger -> bigInteger.longValue();
            case BigDecimal bigDecimal -> bigDecimal.longValue();
            case Boolean b -> b ? 1L : 0L;
            case String s -> {
                try {
                    yield NumericLexer.parseLong(s);
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
        return convertToDouble(value);
    }
    public static Double convertToDouble(Object value) {
        return switch (value) {
            case Integer i -> i.doubleValue();
            case Long l -> l.doubleValue();
            case Double v -> v;
            case BigInteger bigInteger -> bigInteger.doubleValue();
            case BigDecimal bigDecimal -> bigDecimal.doubleValue();
            case Boolean b -> b ? 1.0 : 0.0;
            case String s -> {
                try {
                    yield NumericLexer.parseDouble(s);
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
        return converToBigInteger(value);
    }
    public static BigInteger converToBigInteger(Object value) {
        return switch (value) {
            case Integer i -> BigInteger.valueOf(i);
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
        return converToBigDecimal(value);
    }
    public static BigDecimal converToBigDecimal(Object value) {
        return switch (value) {
            case Integer i -> BigDecimal.valueOf(i);
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
        return convertToString(value);
    }
    public static String convertToString(Object value) {
        return switch (value) {
            case Integer i -> Integer.toString(i);
            case Long l -> Long.toString(l);
            case Double v -> Double.toString(v);
            case BigInteger bigInteger -> bigInteger.toString();
            case BigDecimal bigDecimal -> bigDecimal.toString();
            case Boolean b -> Boolean.toString(b);
            case String s -> s;
            case null, default -> "" + value;
        };
    }

    public static Block convertToBLock(Object value){
        return switch (value) {
            case Long v -> new LongConstant(v);
            case Integer v -> new LongConstant(convertToLong(v));
            case Short v -> new LongConstant(convertToLong(v));
            case Byte v -> new LongConstant(convertToLong(v));
            case Character v -> new LongConstant(convertToLong(v));
            case Float v -> new DoubleConstant(convertToDouble(v));
            case Double v -> new DoubleConstant(v);
            case BigInteger v -> new BigIntegerConstant(v);
            case BigDecimal v -> new BigDecimalConstant(v);
            case String s -> new StringConstant(s);
            case Block block -> block;
            default -> new ObjectConstant(value);
        };
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
