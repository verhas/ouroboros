package com.javax0.ouroboros;

public interface ContextAgent {
    /**
     * Register the commands the package provides in the context.
     * @param context the context to register the objects in
     */
    void register(Context context, Interpreter interpreter);
}
