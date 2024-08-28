package com.javax0.ouroboros;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javax0.ouroboros.AssertUtils.assertOutput;
import static com.javax0.ouroboros.AssertUtils.output;

public class TestSimpleExecutor {

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
    @DisplayName("add three float numbers")
    void addThreeFloatNumbers() throws Exception {
        Assertions.assertTrue(output("puts add 1.2 add 1.3 1.4").startsWith("3.9000"));
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
    @DisplayName("creating object setting fields and getting fields object calculated")
    void objectFieldCalculated() throws Exception {
        assertOutput("object a {} setf {a} b 1 puts field {a} b", "1");
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

    @Test
    @DisplayName("test simple eval")
    void eval() throws Exception {
        assertOutput("puts eval 1", "1");
    }

    @Test
    @DisplayName("test eval string")
    void evalString() throws Exception {
        assertOutput("puts eval {\"add 1 1\"}", "2");
    }

    @Test
    @DisplayName("test eval string in variable")
    void evalStringInVar() throws Exception {
        assertOutput("set a \"b\" set b 3 puts eval a", "3");
    }

    @Test
    @DisplayName("test exec block")
    void exec() throws Exception {
        assertOutput("{ set q arg q q } { puts 3 }", "33");
    }

    @Test
    @DisplayName("test exec block with shift")
    void execShift() throws Exception {
        assertOutput("{ set q arg q 4 q 5 q 6} { puts shift }", "456");
    }

    @Test
    @DisplayName("test multipe add")
    void execMultipleAdd() throws Exception {
        assertOutput("puts add* 1 2 3 {} puts \" \" puts { add* 1 2 3}", "6 6");
    }

    @Test
    @DisplayName("test binary and multipe sub")
    void execMultipleSub() throws Exception {
        assertOutput("puts sub* 1 2 3 {} puts \" \" puts { sub* 1 2 3} puts sub 5 2", "-4 -43");
    }

    @Test
    @DisplayName("test string sub")
    void execStringSub() throws Exception {
        assertOutput("puts sub \"alma ata\" \"ata\"", "alma ");
    }

    @Test
    @DisplayName("test binary and multiple multiplication")
    void execMultipleMul() throws Exception {
        assertOutput("puts mul* 1 3 3 {} puts \" \" puts { mul* 1 3 3} puts mul 5 2", "9 910");
    }

    @Test
    @DisplayName("test string multiplication")
    void execStringMul() throws Exception {
        assertOutput("puts mul \"a\" 3", "aaa");
    }

    @Test
    @DisplayName("test binary and multiple div")
    void execMultipleDiv() throws Exception {
        assertOutput("puts div* 20 2 5 {} puts \" \" puts { div* 20 5 2} puts div 5 2", "2 22");
    }

    @Test
    @DisplayName("test eq")
    void testEq() throws Exception {
        assertOutput("puts eq* 20 2 5 {} puts { eq* 20 20 20} puts eq 5 5 2", "falsetruetrue");
    }

    @Test
    @DisplayName("test lt")
    void testLt() throws Exception {
        assertOutput("puts lt* 20 30 19 {} puts { lt* 20 21 22} puts lt 1 2 0", "falsetruetrue");
    }

    @Test
    @DisplayName("test le")
    void testLe() throws Exception {
        assertOutput("puts le* 20 20 19 {} puts { le* 20 20 21} puts le 20 21 19", "falsetruetrue");
    }

    @Test
    @DisplayName("test gt")
    void testGt() throws Exception {
        assertOutput("puts gt* 20 19 21 {} puts { gt* 20 19 18} puts gt 5 4 6", "falsetruetrue");
    }

    @Test
    @DisplayName("test not")
    void testNot() throws Exception {
        assertOutput("puts not true", "false");
    }

    @Test
    @DisplayName("test ge")
    void testGe() throws Exception {
        assertOutput("puts ge* 20 2 21 {} puts { ge* 20 20 20} puts ge 5 5 6", "falsetruetrue");
    }

    @Test
    @DisplayName("test ne")
    void testNe() throws Exception {
        assertOutput("puts ne* 20 20 20 {} puts { ne* 20 2 2} puts ne 5 2 5", "falsetruetrue");
    }

    @Test
    @DisplayName("test or")
    void testOr() throws Exception {
        // after true skips till {}
        assertOutput("puts or* true puts 2 {}", "true");

        // after true skips till the block end
        assertOutput("puts {or* true puts 2}", "true");

        // it one works because the second arument is a block. It is fetched but not evaluated.
        assertOutput("puts or true {puts 2}", "true");

        // this one does not work, because or will fetch but does not evaluate PUTS as the second argument
        // after that the result of the block is 2 that 'PUTS' does not fetch as it was not invoked
        assertOutput("puts {or true puts 2}", "2");

        // after false it evaluates the puts and the resultof it is 2
        assertOutput("puts or false {puts 2}", "2true");

        assertOutput("puts {or false puts 0}", "0false");
    }

    @Test
    @DisplayName("test and")
    void testAnd() throws Exception {
        assertOutput("puts and* false puts 2 {}", "false");

        assertOutput("puts {and* false puts 2}", "false");

        // this one works because the second argument is a block. It is fetched but not evaluated.
        assertOutput("puts and false {puts 2}", "false");

        // this one does not work, because or will fetch but does not evaluate PUTS as the second argument
        // after that the result of the block is 2 that 'PUTS' does not fetch as it was not invoked
        assertOutput("puts {and false puts 2}", "2");

        // after false it evaluates the puts and the resultof it is 2
        assertOutput("puts and true {puts 2}", "2true");

        assertOutput("puts {and true puts 0}", "0false");
    }

    @Test
    @DisplayName("test Boolean")
    void testBoolean() throws Exception {
        assertOutput("puts bool \"true\"", "true");
    }

    @Test
    @DisplayName("test Double")
    void testDouble() throws Exception {
        assertOutput("puts double \"3.141592674365498\"", "3.141592674365498");
    }

    @Test
    @DisplayName("test Long")
    void testLong() throws Exception {
        assertOutput("puts long \"3\"", "3");
    }

    @Test
    @DisplayName("test BigDecimal")
    void testBigDecimal() throws Exception {
        final var bigInteger = "2000000000000.2300000000000000000000000000000000000000000000000000000000000007";
        assertOutput("puts BigDecimal \"" + bigInteger + "\"", bigInteger);
    }

    @Test
    @DisplayName("test BigInteger")
    void testBigInteger() throws Exception {
        final var bigInteger = "200000000000000000000000000000000000000000000000000000000000000000000000000";
        assertOutput("puts BigInteger \"" + bigInteger + "\"", bigInteger);
    }

    @Test
    @DisplayName("test conditional execution")
    void testConditionalExecution() throws Exception {
        assertOutput("puts if true 1 2", "1");
        assertOutput("puts if add 0 0 1 2", "2");
        assertOutput("puts {if 0 1}", "null");
        assertOutput("puts if 0 1 {}", "null");
    }

    @Test
    @DisplayName("test while loop")
    void testWhileLoop() throws Exception {
        assertOutput("set a 1 " +
                "while{setf $$ a add field $$ a 1 lt a 10}{puts a}", "23456789");
    }

    @Test
    @DisplayName("test quote")
    void testQuote1() throws Exception {
        assertOutput("set a 1 " +
                "set s \"\" " +
                "set source quote eval " +
                "puts {source {while{setg a add a 1 lt a 10}{setg s add s \"1\"} s}}", "11111111");
    }

    @Test
    @DisplayName("test quote")
    void testQuote2() throws Exception {
        assertOutput("setg f 0 " +
                "set a quote {setg f add f 1 f} " +
                "puts a " +
                "puts a " +
                "puts a " +
                "puts a ", "1234");
    }

    @Test
    @DisplayName("test quote")
    void testQuote3() throws Exception {
        assertOutput("set a 1 " +
                "set b quote a " +
                "puts b " +
                "set a 2 " +
                "puts b", "12");
    }
}





