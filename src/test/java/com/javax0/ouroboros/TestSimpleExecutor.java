package com.javax0.ouroboros;

import com.javax0.ouroboros.interpreter.SimpleExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestSimpleExecutor {


    private void assertOutput(final String program, final String expected) throws Exception {
        final var executor = new SimpleExecutor();
        try (final var baos = new ByteArrayOutputStream();
             final var out = new PrintStream(baos)) {
            executor.setOutput(out);
            executor.execute(program);
            final var result = baos.toString(StandardCharsets.UTF_8);
            Assertions.assertEquals(expected, result);
        }
    }

    @Test
    @DisplayName("Hello world test")
    void helloWorld() throws Exception {
        assertOutput("puts \"Hello, world!\"", "Hello, world!");
    }

    @Test
    @DisplayName("multiple commands")
    void multipleCommands() throws Exception {
        assertOutput("puts 1 puts 1", "11");
    }

    @Test
    @DisplayName("add two numbers")
    void addTwoNumbers() throws Exception {
        assertOutput("puts add 1 1", "2");
    }

    @Test
    @DisplayName("add three numbers")
    void addThreeNumbers() throws Exception {
        assertOutput("puts add 1 add 1 1", "3");
    }

    @Test
    @DisplayName("set variable")
    void setVariable() throws Exception {
        assertOutput("set a 1 puts a", "1");
    }

    @Test
    @DisplayName("execute block")
    void executeBlock() throws Exception {
        assertOutput("puts { 1 }", "1");
    }

    @Test
    @DisplayName("execute block multiple content")
    void executeMultiBlock() throws Exception {
        assertOutput("puts { 3 2 1 }", "1");
    }

    @Test
    @DisplayName("execute block in block content")
    void executeNestedBlock() throws Exception {
        assertOutput("puts { 3 2 { 1 }}", "1");
    }

    @Test
    @DisplayName("execute block in block content complex")
    void executeNestedNestedBlock() throws Exception {
        assertOutput("puts { {puts {\"Hali\"}} 3 2 { 1 }}", "Hali1");
    }

    @Test
    @DisplayName("calculated variable name")
    void setCalculatedVariableName() throws Exception {
        assertOutput("set a \"b\" set { a } 2 puts b", "2");
    }

    @Test
    @DisplayName("test variable locality")
    void localVariableNestedNestedBlock() throws Exception {
        assertOutput("set a 1 " +
                "set b 2" +
                "{ set b 3 " +
                "puts b " +
                "puts a " +
                "} puts b puts a", "3121");
    }

    @Test
    @DisplayName("test variable global variable setting")
    void globalVariableNestedNestedBlock() throws Exception {
        assertOutput("set a 1 " +
                "set b 2" +
                "{ setg b 3 " +
                "puts b " +
                "puts a " +
                "} " +
                "puts b " +
                "puts a ", "3131");
    }

    @Test
    @DisplayName("test function definition and calling")
    void functionCall() throws Exception {
        assertOutput("fun a { puts shift } a { shift } 3", "3");
    }

    @Test
    @DisplayName("test function local variables")
    void functionLocalVariables() throws Exception {
        assertOutput("set k 1 fun a { set k 2 puts shift puts k } a k ", "12");
    }

    @Test
    @DisplayName("creating object setting fields and getting fields")
    void objectField() throws Exception {
        assertOutput("object a {} setf a b 1 puts field a b", "1");
    }

    @Test
    @DisplayName("creating object calling methods")
    void objectMethod() throws Exception {
        assertOutput("object a {} method a m { puts field this b } setf a b 3 call a m", "3");
    }

    @Test
    @DisplayName("creating object calling methods nesting")
    void objectMethodNesting() throws Exception {
        assertOutput("object a {} object b {} " +
                "method a m { puts field this b } " +
                "method b m { call a m } " +
                "setf a b 3 call b m", "3");
    }

    @Test
    @DisplayName("creating object calling methods nesting")
    void objectMethodNestingThis() throws Exception {
        assertOutput("object a {} object b {} " +
                "method a m { puts field this b } " +
                "method b m { call a {field this z} } " +
                "setf b z \"m\"" +
                "setf a b 3 call b m", "3");
    }

}





