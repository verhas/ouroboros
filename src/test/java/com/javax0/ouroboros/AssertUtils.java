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

    static void assertOutput(final String program, final String expected) throws Exception {
        Assertions.assertEquals(expected, output(program));
    }
}

