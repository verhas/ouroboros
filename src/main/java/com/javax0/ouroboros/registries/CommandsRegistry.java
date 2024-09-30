package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.ContextAgent;
import com.javax0.ouroboros.Interpreter;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.commands.base.*;
import com.javax0.ouroboros.commands.control.CommandIf;
import com.javax0.ouroboros.commands.control.CommandSwitch;
import com.javax0.ouroboros.commands.control.CommandWhile;
import com.javax0.ouroboros.commands.list.*;
import com.javax0.ouroboros.commands.ops.*;
import com.javax0.ouroboros.commands.string.*;

import java.lang.reflect.InvocationTargetException;

public class CommandsRegistry implements ContextAgent {

    private static class Registerer {
        final Context context;
        final Interpreter interpreter;

        private Registerer(Context context, Interpreter interpreter) {
            this.context = context;
            this.interpreter = interpreter;
        }

        void command(String name, Class<?> commandClass) {
            try {
                final var command = commandClass.getConstructor(Interpreter.class).newInstance(interpreter);
                context.set(name, new SimpleValue<>(command));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        void register(String name, Object obj) {
            context.set(name, new SimpleValue<>(obj));
        }
    }


    @Override
    public void register(Context context, Interpreter interpreter) {
        var registerer = new Registerer(context, interpreter);
        registerer.command("puts", CommandPuts.class);
        registerer.command("add", CommandAdd.class);
        registerer.command("sub",  CommandSub.class);
        registerer.command("mul",  CommandMul.class);
        registerer.command("div",  CommandDiv.class);
        registerer.command("mod",  CommandMod.class);
        registerer.command("eq",  CommandEq.class);
        registerer.command("ne",  CommandNe.class);
        registerer.command("le",  CommandLe.class);
        registerer.command("ge",  CommandGe.class);
        registerer.command("lt",  CommandLt.class);
        registerer.command("gt",  CommandGt.class);
        registerer.command("and",  CommandAnd.class);
        registerer.command("or",  CommandOr.class);
        registerer.command("if",  CommandIf.class);
        registerer.command("switch",  CommandSwitch.class);
        registerer.command("while",  CommandWhile.class);
        registerer.command("include",  CommandInclude.class);
        registerer.command("error",  CommandError.class);
        registerer.command("set",  CommandSet.class);
        registerer.command("setg",  CommandSetg.class);
        registerer.command("setn",  CommandSetn.class);
        registerer.command("setf",  CommandSetf.class);
        registerer.command("sets",  CommandSets.class);
        registerer.command("field",  CommandField.class);
        registerer.command("call",  CommandCall.class);
        registerer.command("source",  CommandSource.class);
        registerer.command("length",  CommandLength.class);
        registerer.command("indexOf",  CommandIndexOf.class);
        registerer.command("isBlank",  CommandIsBlank.class);
        registerer.command("isEmpty",  CommandIsEmpty.class);
        registerer.command("replace",  CommandReplace.class);
        registerer.command("replaceAll",  CommandReplaceAll.class);
        registerer.command("replaceFirst",  CommandReplaceFirst.class);
        registerer.command("substring",  CommandSubstring.class);
        registerer.command("lc",  CommandTolower.class);
        registerer.command("uc",  CommandToupper.class);
        registerer.command("trim",  CommandTrim.class);
        registerer.command("fixup",  CommandFixup.class);
        registerer.command("BigInteger",  CommandBigInteger.class);
        registerer.command("BigDecimal",  CommandBigDecimal.class);
        registerer.command("long",  CommandLong.class);
        registerer.command("double",  CommandDouble.class);
        registerer.command("bool",  CommandBoolean.class);
        registerer.command("not",  CommandNot.class);
        registerer.command("string",  CommandString.class);
        registerer.command("bw",  CommandBW.class);
        registerer.command("quote",  CommandQuote.class);
        registerer.command("closure",  CommandClosure.class);
        registerer.command("'",  CommandQuote.class);
        registerer.register("$symbolChars","'");
        registerer.command("list",  CommandList.class);
        registerer.command("first",  CommandListFirst.class);
        registerer.command("car",  CommandListFirst.class);
        registerer.command("rest",  CommandListRest.class);
        registerer.command("cdr",  CommandListRest.class);
        registerer.command("insert",  CommandListInsert.class);
        registerer.command("at",  CommandAt.class);
        registerer.command("split",  CommandListSplit.class);
        registerer.command("setl",  CommandListSet.class);
        registerer.command("last",  CommandListLast.class);
        registerer.command("shift",  CommandShift.class);
        registerer.command("arg",  CommandArg.class);
        registerer.command("eval",  CommandEval.class);
        registerer.command("object",  CommandObject.class);
        registerer.command("copy",  CommandCopy.class);

        registerer.register("null", null);
        registerer.register("true", true);
        registerer.register("false", false);
    }
}
