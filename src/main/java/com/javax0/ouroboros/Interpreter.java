package com.javax0.ouroboros;

import java.util.List;

/**
 * The interpreter object that calls the commnds and that the commands can interact with.
 */
public interface Interpreter {

    Source source();

    /**
     * Pop the top block from the stack.
     * Commands can use this method to get the block that was on the top of the stack.
     *
     * @return the block that was on the top of the stack
     */
    Block pop();

    /**
     * Execute the program that is the input.
     * This is the main entry of the interpreter.
     *
     * @param input the command to execute
     */
    void execute(String input);

    /**
     * Push a block on the stack. Commands usually call this method when they fetched too many blocks from the stack.
     * For example an 'if' command may fetch the condition block and the then block from the stack. After that it
     * fetches the next block, which may be an 'else' or 'elseif' string, but if it is not, then the command has to
     * push back the block that was fetched as the next block.
     * <p>
     * Commands can create arbitrary blocks to push back. It is not a must that they push back the block that was
     * actually fetched.
     *
     * @param block the block to push back on the stack
     */
    void push(Block block);

    /**
     * Push all the blocks from the list on the stack.
     *
     * @param blocks the blocks to push on the stack
     */
    void pushAll(List<Block> blocks);

    /**
     * Open a new stack frame to execute a block.
     */
    ObjectValue down();

    /**
     * Open a new stack frame to execute a list of blocks.
     *
     * @param state the blocks to put on the stack
     */
    void down(State state);

    /**
     * Close the current stack frame and return to the previous one.
     */
    State up();

    /**
     * Evaluare a block and return the value.
     *
     * @param block the block to evaluate
     * @param <T>   the type of the value that the block evaluates to
     * @return the value that the block evaluates to
     */
    <T> Value<T> evaluate(Context context, Block block);

    /**
     * Output a message. This method is used by the commands to output messages. The output can be anything.
     * By default all output are printed to the standard output.
     *
     * @param message the message to output
     */
    void output(String message);

    void include(String fn);

    interface State {
    }
}
