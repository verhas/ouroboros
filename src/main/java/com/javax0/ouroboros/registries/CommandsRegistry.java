package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.base.*;
import com.javax0.ouroboros.commands.control.CommandIf;
import com.javax0.ouroboros.commands.control.CommandSwitch;
import com.javax0.ouroboros.commands.control.CommandWhile;
import com.javax0.ouroboros.commands.list.*;
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
        context.<Command<?>>set("mod", new SimpleValue<>(new CommandMod<>(interpreter)));
        context.<Command<?>>set("eq", new SimpleValue<>(new CommandEq(interpreter)));
        context.<Command<?>>set("ne", new SimpleValue<>(new CommandNe(interpreter)));
        context.<Command<?>>set("le", new SimpleValue<>(new CommandLe(interpreter)));
        context.<Command<?>>set("ge", new SimpleValue<>(new CommandGe(interpreter)));
        context.<Command<?>>set("lt", new SimpleValue<>(new CommandLt(interpreter)));
        context.<Command<?>>set("gt", new SimpleValue<>(new CommandGt(interpreter)));
        context.<Command<?>>set("and", new SimpleValue<>(new CommandAnd(interpreter)));
        context.<Command<?>>set("or", new SimpleValue<>(new CommandOr(interpreter)));
        context.<Command<?>>set("if", new SimpleValue<>(new CommandIf<>(interpreter)));
        context.<Command<?>>set("switch", new SimpleValue<>(new CommandSwitch<>(interpreter)));
        context.<Command<?>>set("while", new SimpleValue<>(new CommandWhile<>(interpreter)));
        context.<Command<?>>set("error", new SimpleValue<>(new CommandError(interpreter)));

        context.<Command<?>>set("set", new SimpleValue<>(new CommandSet(interpreter)));
        context.<Command<?>>set("setg", new SimpleValue<>(new CommandSetg(interpreter)));
        context.<Command<?>>set("setn", new SimpleValue<>(new CommandSetn(interpreter)));
        context.<Command<?>>set("setf", new SimpleValue<>(new CommandSetf(interpreter)));
        context.<Command<?>>set("sets", new SimpleValue<>(new CommandSets(interpreter)));

        context.<Command<?>>set("field", new SimpleValue<>(new CommandField<>(interpreter)));
        context.<Command<?>>set("call", new SimpleValue<>(new CommandCall<>(interpreter)));
        context.<Command<?>>set("source", new SimpleValue<>(new CommandSource(interpreter)));

        context.<Command<?>>set("length", new SimpleValue<>(new CommandLength(interpreter)));
        context.<Command<?>>set("indexOf", new SimpleValue<>(new CommandIndexOf(interpreter)));
        context.<Command<?>>set("isBlank", new SimpleValue<>(new CommandIsBlank(interpreter)));
        context.<Command<?>>set("isEmpty", new SimpleValue<>(new CommandIsEmpty(interpreter)));
        context.<Command<?>>set("replace", new SimpleValue<>(new CommandReplace(interpreter)));
        context.<Command<?>>set("replaceAll", new SimpleValue<>(new CommandReplaceAll(interpreter)));
        context.<Command<?>>set("replaceFirst", new SimpleValue<>(new CommandReplaceFirst(interpreter)));
        context.<Command<?>>set("substring", new SimpleValue<>(new CommandSubstring(interpreter)));
        context.<Command<?>>set("lc", new SimpleValue<>(new CommandTolower(interpreter)));
        context.<Command<?>>set("uc", new SimpleValue<>(new CommandToupper(interpreter)));
        context.<Command<?>>set("trim", new SimpleValue<>(new CommandTrim(interpreter)));

        context.<Command<?>>set("fixup", new SimpleValue<>(new CommandFixup(interpreter)));


        context.<Command<?>>set("BigInteger", new SimpleValue<>(new CommandBigInteger(interpreter)));
        context.<Command<?>>set("BigDecimal", new SimpleValue<>(new CommandBigDecimal(interpreter)));
        context.<Command<?>>set("long", new SimpleValue<>(new CommandLong(interpreter)));
        context.<Command<?>>set("double", new SimpleValue<>(new CommandDouble(interpreter)));
        context.<Command<?>>set("bool", new SimpleValue<>(new CommandBoolean(interpreter)));
        context.<Command<?>>set("not", new SimpleValue<>(new CommandNot(interpreter)));
        context.<Command<?>>set("string", new SimpleValue<>(new CommandString(interpreter)));
        context.<Command<?>>set("bw", new SimpleValue<>(new CommandBW<>(interpreter)));
        context.<Command<?>>set("quote", new SimpleValue<>(new CommandQuote<>(interpreter)));
        context.<Command<?>>set("closure", new SimpleValue<>(new CommandClosure<>(interpreter)));
        context.<Command<?>>set("'", new SimpleValue<>(new CommandQuote<>(interpreter)));
        context.<Command<?>>set("list", new SimpleValue<>(new CommandList<>(interpreter)));
        context.<Command<?>>set("first", new SimpleValue<>(new CommandListFirst<>(interpreter)));
        context.<Command<?>>set("car", new SimpleValue<>(new CommandListFirst<>(interpreter)));
        context.<Command<?>>set("rest", new SimpleValue<>(new CommandListRest<>(interpreter)));
        context.<Command<?>>set("cdr", new SimpleValue<>(new CommandListRest<>(interpreter)));
        context.<Command<?>>set("insert", new SimpleValue<>(new CommandListInsert<>(interpreter)));
        context.<Command<?>>set("at", new SimpleValue<>(new CommandAt<>(interpreter)));
        context.<Command<?>>set("split", new SimpleValue<>(new CommandListSplit<>(interpreter)));
        context.<Command<?>>set("setl", new SimpleValue<>(new CommandListSet<>(interpreter)));
        context.<Command<?>>set("last", new SimpleValue<>(new CommandListLast<>(interpreter)));

        context.<Command<?>>set("shift", new SimpleValue<>(new CommandShift<>(interpreter)));
        context.<Command<?>>set("arg", new SimpleValue<>(new CommandArg<>(interpreter)));
        context.<Command<?>>set("eval", new SimpleValue<>(new CommandEval<>(interpreter)));
        context.<Command<?>>set("object", new SimpleValue<>(new CommandObject(interpreter)));
        context.<Command<?>>set("copy", new SimpleValue<>(new CommandCopy<>(interpreter)));

        context.set("null", new SimpleValue<>(null));
        context.set("true", new SimpleValue<>(true));
        context.set("false", new SimpleValue<>(false));
    }
}
