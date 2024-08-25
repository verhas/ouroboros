package com.javax0.ouroboros.commands.lexers;

import com.javax0.ouroboros.Context;
import com.javax0.ouroboros.SimpleValue;
import com.javax0.ouroboros.Value;
import com.javax0.ouroboros.commands.AbstractCommand;
import com.javax0.ouroboros.commands.base.BareWord;
import com.javax0.ouroboros.commands.pseudo.CommandBlockClose;

public class BlockCloseLexer<T> extends AbstractCommand<CommandBlockClose> {

    private static final Value<CommandBlockClose> instance = new SimpleValue<>(new CommandBlockClose());

    @Override
    public Value<CommandBlockClose> execute(Context context) {
        final var source = interpreter.source();
        if (source == null) {
            return null;
        }
        final var input = source.execute(context).get();
        if (!input.isEmpty() && input.charAt(0) == '}') {
            final var rest = input.substring(1);
            source.update(rest);
            return instance;
        }
        return null;
    }

}
