package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.lexers.*;

import java.util.ArrayList;
import java.util.List;

public class LexerRegistry implements ContextAgent {
    @Override
    public void register(Context context, Interpreter interpreter) {
        final var lexers = context.<List<Command<?>>>variable("$lex").orElseGet(() -> new MutableValue<>(new ArrayList<>()));
        lexers.get().add(new BareWordLexer<>());
        lexers.get().add(new StringLexer());
        lexers.get().add(new NumericLexer());
        lexers.get().add(new SpaceLexer<>());
        lexers.get().add(new BlockLexer<>());
        lexers.get().add(new BlockCloseLexer<>());
        context.set("$lex", lexers);
        for( final var lexer : lexers.get()){
            lexer.set(interpreter);
        }
    }
}
