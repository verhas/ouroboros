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
            lexers.get().values().add(Value.of(command));
        }
    }

    @Override
    public void register(Context context, Interpreter interpreter) {
        final var lexers = context.<ListValue<Command<?>>>variable("$lex").orElseGet(() -> new SimpleValue<>(new ListValue<>(interpreter)));
        final var registerer = new Registerer(context, interpreter, lexers);
        /*
        snippet var_keyword
        =H `$keyword`

        This variable references the lexer that recognizes a bare word.
        It is called keyword, but bear in mind that there are no keywords in UR.

        end snippet
         */
        registerer.register("$keyword", new BareWordLexer<>(interpreter));
        /*
        snippet var_string
        =H `$string`

        This variable references the lexer that recognizes a string.
        end snippet
         */
        registerer.register("$string", new StringLexer(interpreter));
        /*
        snippet var_number
        =H `$number`

        This variable references the lexer that recognizes a number.
        end snippet
         */
        registerer.register("$number", new NumericLexer(interpreter));
        /*
        snippet var_space
        =H `$number`

        This variable references the lexer that recognizes a space.
        end snippet
         */
        registerer.register("$space", new SpaceLexer<>(interpreter));
        /*
        snippet var_block
        =H `$number`

        This variable references the lexer that recognizes a whole block.
        This analyzer is more like a syntax analyzer than a lexer.
        It will call the interpreter recursively to call the lexers for the commands in the block until it finds a block close.
        end snippet
         */
        registerer.register("$block", new BlockLexer<>(interpreter));
        /*
        snippet var_blockOpen
        =H `$blockOpen`

        This variable references the lexer that recognizes a block start.
        This lexer is called by the lexer that recognizes a block and is not in the list of alternative lexers.
        end snippet
        */
        context.<BlockOpenLexer>set("$blockOpen",Value.of(new BlockOpenLexer(interpreter)));
        /*
        snippet var_blockClose
        =H `$blockClose`

        This variable references the lexer that recognizes a block end.
        end snippet
        */
        registerer.register("$blockClose", new BlockCloseLexer<>(interpreter));
        /*
        snippet var_symbol
        =H `$symbol`

        This variable references the lexer that recognizes a symbol.
        This lexer will accept a string that contains symbol characters.
        Everything is a symbol character that is not a Java identifier character, not white space, not a digit, and not a block start or end character.
        end snippet
        */
        registerer.register("$symbol", new SymbolLexer<>(interpreter));
        context.set("$lex", lexers);
    }

}
