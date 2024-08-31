package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.lexers.*;
import com.javax0.ouroboros.commands.list.ListValue;

public class LexerRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        final var lexers = context.<ListValue<Command<?>>>variable("$lex").orElseGet(() -> new SimpleValue<>(new ListValue<>(interpreter)));
        register(lexers,new BareWordLexer<>());
        register(lexers,new StringLexer());
        register(lexers,new NumericLexer());
        register(lexers,new SpaceLexer<>());
        register(lexers,new BlockLexer<>());
        register(lexers,new BlockCloseLexer<>());
        register(lexers,new SymbolLexer<>());
        context.set("$lex", lexers);
        for( final var lexer : lexers.get().values()){
            lexer.get().set(interpreter);
        }
    }

    private void register(Value<ListValue<Command<?>>> lexers, final Command<?> command) {
        lexers.get().values().add(new SimpleValue<>(command));
    }
}
