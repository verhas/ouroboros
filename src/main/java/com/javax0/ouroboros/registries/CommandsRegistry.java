package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.base.*;
import com.javax0.ouroboros.commands.control.CommandIf;
import com.javax0.ouroboros.commands.control.CommandWhile;
import com.javax0.ouroboros.commands.ops.*;
import com.javax0.ouroboros.commands.string.*;

public class CommandsRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        context.<Command<?>>set("puts", new SimpleValue<>(new CommandPuts(interpreter)));
        context.<Command<?>>set("add", new SimpleValue<>(new CommandAdd<>(interpreter)));
        context.<Command<?>>set("sub", new SimpleValue<>(new CommandSub<>(interpreter)));
        context.<Command<?>>set("mul", new SimpleValue<>(new CommandMul<>(interpreter)));
        context.<Command<?>>set("div", new SimpleValue<>(new CommandDiv<>(interpreter)));
        context.<Command<?>>set("eq", new SimpleValue<>(new CommandEq(interpreter)));
        context.<Command<?>>set("ne", new SimpleValue<>(new CommandNe(interpreter)));
        context.<Command<?>>set("le", new SimpleValue<>(new CommandLe(interpreter)));
        context.<Command<?>>set("ge", new SimpleValue<>(new CommandGe(interpreter)));
        context.<Command<?>>set("lt", new SimpleValue<>(new CommandLt(interpreter)));
        context.<Command<?>>set("gt", new SimpleValue<>(new CommandGt(interpreter)));
        context.<Command<?>>set("and", new SimpleValue<>(new CommandAnd(interpreter)));
        context.<Command<?>>set("or", new SimpleValue<>(new CommandOr(interpreter)));
        context.<Command<?>>set("if", new SimpleValue<>(new CommandIf<>(interpreter)));
        context.<Command<?>>set("while", new SimpleValue<>(new CommandWhile<>(interpreter)));
        context.<Command<?>>set("set", new SimpleValue<>(new CommandSet(interpreter)));
        context.<Command<?>>set("setg", new SimpleValue<>(new CommandSetg(interpreter)));
        context.<Command<?>>set("setf", new SimpleValue<>(new CommandSetf(interpreter)));
        context.<Command<?>>set("field", new SimpleValue<>(new CommandField<>(interpreter)));
        context.<Command<?>>set("call", new SimpleValue<>(new CommandCall<>(interpreter)));


        context.<Command<?>>set("charAt", new SimpleValue<>(new CommandCharAt(interpreter)));
        context.<Command<?>>set("isBlank", new SimpleValue<>(new CommandIsBlank(interpreter)));
        context.<Command<?>>set("isEmpty", new SimpleValue<>(new CommandIsEmpty(interpreter)));
        context.<Command<?>>set("replace", new SimpleValue<>(new CommandReplace(interpreter)));
        context.<Command<?>>set("replaceAll", new SimpleValue<>(new CommandReplaceAll(interpreter)));
        context.<Command<?>>set("replaceFirst", new SimpleValue<>(new CommandReplaceFirst(interpreter)));
        context.<Command<?>>set("substring", new SimpleValue<>(new CommandSubstring(interpreter)));
        context.<Command<?>>set("lc", new SimpleValue<>(new CommandTolower(interpreter)));
        context.<Command<?>>set("uc", new SimpleValue<>(new CommandToupper(interpreter)));
        context.<Command<?>>set("trim", new SimpleValue<>(new CommandTrim(interpreter)));


        context.<Command<?>>set("BigInteger", new SimpleValue<>(new CommandBigInteger(interpreter)));
        context.<Command<?>>set("BigDecimal", new SimpleValue<>(new CommandBigDecimal(interpreter)));
        context.<Command<?>>set("long", new SimpleValue<>(new CommandLong(interpreter)));
        context.<Command<?>>set("double", new SimpleValue<>(new CommandDouble(interpreter)));
        context.<Command<?>>set("bool", new SimpleValue<>(new CommandBoolean(interpreter)));
        context.<Command<?>>set("not", new SimpleValue<>(new CommandNot(interpreter)));
        context.<Command<?>>set("string", new SimpleValue<>(new CommandString(interpreter)));
        context.<Command<?>>set("quote", new SimpleValue<>(new CommandQuote<>(interpreter)));
        context.<Command<?>>set("'", new SimpleValue<>(new CommandQuote<>(interpreter)));

        context.<Command<?>>set("shift", new SimpleValue<>(new CommandShift<>(interpreter)));
        context.<Command<?>>set("arg", new SimpleValue<>(new CommandArg<>(interpreter)));
        context.<Command<?>>set("eval", new SimpleValue<>(new CommandEval<>(interpreter)));
        context.<Command<?>>set("object", new SimpleValue<>(new CommandObject(interpreter)));

        context.set("null", new SimpleValue<>(null));
        context.set("true", new SimpleValue<>(true));
        context.set("false", new SimpleValue<>(false));
    }
}
