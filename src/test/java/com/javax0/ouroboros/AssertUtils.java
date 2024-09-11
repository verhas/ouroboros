package com.javax0.ouroboros;

import com.javax0.ouroboros.interpreter.SimpleExecutor;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

class AssertUtils {
    static String output(final String program) throws Exception {
        final var executor = new SimpleExecutor();
        try (final var baos = new ByteArrayOutputStream();
             final var out = new PrintStream(baos)) {
            executor.setOutput(out);
            executor.execute(program);
            return baos.toString(StandardCharsets.UTF_8);
        }
    }

    public static String getFullExceptionInfo(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");

        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }

        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append("Caused by: ");
            sb.append(getFullExceptionInfo(cause));
        }

        return sb.toString();
    }

    static String outputSafe(final String program) {
        final var executor = new SimpleExecutor();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final var out = new PrintStream(baos)) {
            executor.setOutput(out);
            executor.execute(program);
            return baos.toString(StandardCharsets.UTF_8);
        } catch (final Exception e) {
            return "OUTPUT BEFORE ERROR: "+baos.toString(StandardCharsets.UTF_8) + "\nERROR: " + e.getMessage();//getFullExceptionInfo(e);
        }
    }

    static void assertOutput(final String program, final String expected) throws Exception {
        Assertions.assertEquals(expected, output(program));
    }
}

