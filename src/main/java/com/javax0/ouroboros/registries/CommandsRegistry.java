package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.base.*;

public class CommandsRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        context.<Command<?>>set("puts", new SimpleValue<>(new CommandPuts(interpreter)));
        context.<Command<?>>set("add", new SimpleValue<>(new CommandAdd(interpreter)));
        context.<Command<?>>set("set", new SimpleValue<>(new CommandSet(interpreter)));
        context.<Command<?>>set("setg", new SimpleValue<>(new CommandSetg(interpreter)));
        context.<Command<?>>set("setf", new SimpleValue<>(new CommandSetf(interpreter)));
        context.<Command<?>>set("field", new SimpleValue<>(new CommandField<>(interpreter)));
        context.<Command<?>>set("method", new SimpleValue<>(new CommandMethod(interpreter)));
        context.<Command<?>>set("call", new SimpleValue<>(new CommandCall<>(interpreter)));
        context.<Command<?>>set("fun", new SimpleValue<>(new CommandFun(interpreter)));
        context.<Command<?>>set("shift", new SimpleValue<>(new CommandShift<>(interpreter)));
        context.<Command<?>>set("object", new SimpleValue<>(new CommandObject(interpreter)));

        context.set("true", new SimpleValue<>(true));
        context.set("false", new SimpleValue<>(false));
    }
}
