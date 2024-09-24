package com.javax0.ouroboros.registries;

import com.javax0.ouroboros.*;
import com.javax0.ouroboros.commands.lexers.*;
import com.javax0.ouroboros.commands.list.ListValue;

public class LexerRegistry implements ContextAgent {

    private static class Registerer {
        final Context context;
        final Interpreter interpreter;
        final Value<ListValue<Command<?>>> lexers;

        private Registerer(Context context, Interpreter interpreter, Value<ListValue<Command<?>>> lexers) {
            this.context = context;
            this.interpreter = interpreter;
            this.lexers = lexers;
        }

        private void register(final String kw, final Command<?> command) {
            context.<Command<?>>set(kw, new SimpleValue<>(command));
            lexers.get().values().add(new SimpleValue<>(command));
        }
    }

    @Override
    public void register(Context context, Interpreter interpreter) {
        final var lexers = context.<ListValue<Command<?>>>variable("$lex").orElseGet(() -> new SimpleValue<>(new ListValue<>(interpreter)));
        final var registerer = new Registerer(context, interpreter, lexers);
        registerer.register("$keyword", new BareWordLexer<>(interpreter));
        registerer.register("$string", new StringLexer(interpreter));
        registerer.register("$number", new NumericLexer(interpreter));
        registerer.register("$space", new SpaceLexer<>(interpreter));
        registerer.register("$block", new BlockLexer<>(interpreter));
        registerer.register("$blockClose", new BlockCloseLexer<>(interpreter));
        registerer.register("$symbol", new SymbolLexer<>(interpreter));
        context.set("$lex", lexers);
    }

}
