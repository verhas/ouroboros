package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.lexers.*;
import com.javax0.ouroboros.commands.list.ListValue;

public class LexerRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        final var lexers = context.<ListValue<Command<?>>>variable("$lex").orElseGet(() -> new SimpleValue<>(new ListValue<>(interpreter)));
        register(context, interpreter, lexers, "$keyword", new BareWordLexer<>());
        register(context, interpreter, lexers, "$string", new StringLexer());
        register(context, interpreter, lexers, "$number", new NumericLexer());
        register(context, interpreter, lexers, "$space", new SpaceLexer<>());
        register(context, interpreter, lexers, "$block", new BlockLexer<>());
        register(context, interpreter, lexers, "$blockClose", new BlockCloseLexer<>());
        register(context, interpreter, lexers, "$symbol", new SymbolLexer<>());
        context.set("$lex", lexers);
        for (final var lexer : lexers.get().values()) {
            lexer.get().set(interpreter);
        }
    }

    private void register(Context context, Interpreter interpreter, Value<ListValue<Command<?>>> lexers, final String kw, final Command<?> command) {
        context.<Command<?>>set(kw, new SimpleValue<>(command));
        lexers.get().values().add(new SimpleValue<>(command));
    }
}
