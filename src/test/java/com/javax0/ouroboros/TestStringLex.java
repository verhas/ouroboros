package com.javax0.ouroboros;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javax0.ouroboros.AssertUtils.assertOutput;
import static com.javax0.ouroboros.AssertUtils.output;

public class TestStringLex {

    private static String string(String s) {
        return "puts \"" + s + "\"";
    }

    private static String mlstring(String s) {
        return "puts \"\"\"\n" + s + "\"\"\"";
    }

    @Test
    @DisplayName("Test simple string literals that are syntactically correct")
    void testStringFetchers() throws Exception {
        Assertions.assertEquals("", output(string("")));
        Assertions.assertEquals("a", output(string("a")));
        Assertions.assertEquals("\r", output(string("\\r")));
        Assertions.assertEquals("\n", output(string("\\n")));
        Assertions.assertEquals("\b", output(string("\\b")));
        Assertions.assertEquals("\t", output(string("\\t")));
        Assertions.assertEquals("\f", output(string("\\f")));
        Assertions.assertEquals("'", output(string("\\'")));
        Assertions.assertEquals("\"", output(string("\\\"")));
        Assertions.assertEquals("\773", output(string("\\773")));
        Assertions.assertEquals("\073", output(string("\\073")));
        Assertions.assertEquals("\079", output(string("\\079")));
    }

    @Test
    @DisplayName("Test multi line string literals that are syntactically correct")
    void testMultiLineStringFetchers() throws Exception {
        Assertions.assertEquals("\n", output(mlstring("")));
        Assertions.assertEquals("\na", output(mlstring("a")));
        Assertions.assertEquals("\n\r", output(mlstring("\\r")));
        Assertions.assertEquals("\n\n", output(mlstring("\\n")));
        Assertions.assertEquals("\n\b", output(mlstring("\\b")));
        Assertions.assertEquals("\n\t", output(mlstring("\\t")));
        Assertions.assertEquals("\n\f", output(mlstring("\\f")));
        Assertions.assertEquals("\n'", output(mlstring("\\'")));
        Assertions.assertEquals("\n\"", output(mlstring("\\\"")));
        Assertions.assertEquals("\n\773", output(mlstring("\\773")));
        Assertions.assertEquals("\n\073", output(mlstring("\\073")));
        Assertions.assertEquals("\n\079", output(mlstring("\\079")));
        Assertions.assertEquals("\na\nb", output(mlstring("a\n\rb")));
        Assertions.assertEquals("\na\nb", output(mlstring("a\rb")));
    }

    @Test
    @DisplayName("Test simple string literals that are syntactically incorrect")
    void testBadStringFetchers() {
        Assertions.assertThrows(Exception.class, () -> output(string("\n")));
        Assertions.assertThrows(Exception.class, () -> output(string("\r")));
        Assertions.assertThrows(Exception.class, () -> output(string("\\z")));
        Assertions.assertThrows(Exception.class, () -> output(string("\\")));
        Assertions.assertThrows(Exception.class, () -> output("\""));
        Assertions.assertThrows(Exception.class, () -> output("\"\\"));
        Assertions.assertThrows(Exception.class, () -> output("\"bababa"));
    }

    @Test
    @DisplayName("Test multi line string literals that are syntactically incorrect")
    void testBadMultiLineStringFetchers() {
        Assertions.assertThrows(Exception.class, () -> output("\"\"\""));
        Assertions.assertThrows(Exception.class, () -> output("\"\"\"\""));
        Assertions.assertThrows(Exception.class, () -> output("\"\"\"\"\""));
    }
}
