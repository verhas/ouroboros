import com.javax0.ouroboros.registries.BlockFetchRegistry;
import com.javax0.ouroboros.registries.CommandsRegistry;
import com.javax0.ouroboros.registries.LexerRegistry;

module com.javax0.ouroboros {
    exports com.javax0.ouroboros;
    exports com.javax0.ouroboros.interpreter;
    exports com.javax0.ouroboros.registries;
    exports com.javax0.ouroboros.cmd;
    provides com.javax0.ouroboros.ContextAgent with LexerRegistry, CommandsRegistry, BlockFetchRegistry;
    uses com.javax0.ouroboros.ContextAgent;
}