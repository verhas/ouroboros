package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.base.*;
import com.javax0.ouroboros.commands.ops.*;

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
        context.<Command<?>>set("set", new SimpleValue<>(new CommandSet(interpreter)));
        context.<Command<?>>set("setg", new SimpleValue<>(new CommandSetg(interpreter)));
        context.<Command<?>>set("setf", new SimpleValue<>(new CommandSetf(interpreter)));
        context.<Command<?>>set("field", new SimpleValue<>(new CommandField<>(interpreter)));
        context.<Command<?>>set("method", new SimpleValue<>(new CommandMethod(interpreter)));
        context.<Command<?>>set("call", new SimpleValue<>(new CommandCall<>(interpreter)));
        context.<Command<?>>set("fun", new SimpleValue<>(new CommandFun(interpreter)));

        context.<Command<?>>set("BigInteger", new SimpleValue<>(new CommandBigInteger(interpreter)));
        context.<Command<?>>set("BigDecimal", new SimpleValue<>(new CommandBigDecimal(interpreter)));
        context.<Command<?>>set("long", new SimpleValue<>(new CommandLong(interpreter)));
        context.<Command<?>>set("double", new SimpleValue<>(new CommandDouble(interpreter)));
        context.<Command<?>>set("bool", new SimpleValue<>(new CommandBoolean(interpreter)));
        context.<Command<?>>set("not", new SimpleValue<>(new CommandNot(interpreter)));
        context.<Command<?>>set("string", new SimpleValue<>(new CommandString(interpreter)));

        context.<Command<?>>set("shift", new SimpleValue<>(new CommandShift<>(interpreter)));
        context.<Command<?>>set("arg", new SimpleValue<>(new CommandArg<>(interpreter)));
        context.<Command<?>>set("eval", new SimpleValue<>(new CommandEval<>(interpreter)));
        context.<Command<?>>set("object", new SimpleValue<>(new CommandObject(interpreter)));

        context.set("true", new SimpleValue<>(true));
        context.set("false", new SimpleValue<>(false));
    }
}
