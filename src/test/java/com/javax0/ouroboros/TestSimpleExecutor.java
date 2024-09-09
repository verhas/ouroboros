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
        assertOutput("set a quote{ puts shift } a { shift } 3", "3");
    }

    @Test
    @DisplayName("test function local variables")
    void functionLocalVariables() throws Exception {
        assertOutput("set k 1 set a '{ set k 2 puts shift puts k } a k ", "12");
    }

    @Test
    @DisplayName("creating object setting fields and getting fields")
    void objectField() throws Exception {
        assertOutput("set a object {} setf a b 1 puts field a b", "1");
    }

    @Test
    @DisplayName("creating object setting fields and getting fields vararg")
    void objectFieldVararg() throws Exception {
        assertOutput("set a object {} " +
                "setf a b object {} " +
                "setf field a b c 1 " +
                "puts field* a b c {}", "1");
        assertOutput("set a object {} " +
                "setf a b object {} " +
                "setf field a b c 1 " +
                "{puts field* a b c}", "1");
    }

    @Test
    @DisplayName("creating object setting fields and getting fields object calculated")
    void objectFieldCalculated() throws Exception {
        assertOutput("set a object{} setf {a} b 1 puts field {a} b", "1");
    }

    @Test
    @DisplayName("creating object calling methods")
    void objectMethod1() throws Exception {
        assertOutput("set a object {} setf a m quote{ puts field this b } setf a b 3 call a m", "3");
    }

    @Test
    @DisplayName("creating object calling methods through fields")
    void objectMethod2() throws Exception {
        assertOutput("set a object {} " +
                "setf a b object {} " +
                "setf field a b m quote { puts field this c } " +
                "setf field a b c 3 " +
                "call field a b m", "3");
    }

    @Test
    @DisplayName("creating object calling* methods")
    void objectMethod3() throws Exception {
        assertOutput("set a object {} " +
                "setf a b quote{set x object {} setf x m quote{ puts field this c } setf x c 3 x} " +
                "call* a b m", "3");
    }

    @Test
    @DisplayName("creating object calling methods nesting")
    void objectMethodNesting() throws Exception {
        assertOutput("set a object {} set b object {} " +
                "setf a m quote{ puts field this b } " +
                "setf b m quote{ call a m } " +
                "setf a b 3 call b m", "3");
    }

    @Test
    @DisplayName("creating object calling methods nesting")
    void objectMethodNestingThis() throws Exception {
        assertOutput("set a object{} set b object{} " +
                "setf a m quote{ puts field this b } " +
                "setf b m quote{ call a {field this z} } " +
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
    @DisplayName("test modulo calculation")
    void execMod() throws Exception {
        assertOutput("puts mod 1 3", "1");
        Assertions.assertTrue(output("puts mod 1.21 0.5").startsWith("0.2"));
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
        assertOutput("puts or* true puts 2 {}", "2true");

        // after true skips till the block end
        assertOutput("puts {or* true puts 2}", "2true");

        // it one works because the second arument is a block. It is fetched but not evaluated.
        assertOutput("puts or true {puts 2}", "2true");

        // this one does not work, because or will fetch but does not evaluate PUTS as the second argument
        // after that the result of the block is 2 that 'PUTS' does not fetch as it was not invoked
        assertOutput("puts {or true puts 2}", "2true");

        // after false it evaluates the puts and the resultof it is 2
        assertOutput("puts or false {puts 2}", "2true");

        assertOutput("puts {or false puts 0}", "0false");
    }

    @Test
    @DisplayName("test and")
    void testAnd() throws Exception {
        assertOutput("puts and* false puts 2 {}", "2false");

        assertOutput("puts {and* false puts 2}", "2false");

        // this one works because the second argument is a block. It is fetched but not evaluated.
        assertOutput("puts and false {puts 2}", "2false");

        // this one does not work, because or will fetch but does not evaluate PUTS as the second argument
        // after that the result of the block is 2 that 'PUTS' does not fetch as it was not invoked
        assertOutput("puts {and false puts 2}", "2false");

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


    @DisplayName("charAt")
    @Test
    void testcharAt() throws Exception {
        assertOutput("puts charAt 2 \"balaka\"", "l");
    }

    @DisplayName("isBlank")
    @Test
    void testisBlank() throws Exception {
        assertOutput("puts isBlank \"aaaa\"", "false");
        assertOutput("puts isBlank \"    \"", "true");
        assertOutput("puts isBlank \"\"", "true");
    }

    @DisplayName("isEmpty")
    @Test
    void testisEmpty() throws Exception {
        assertOutput("puts isEmpty \"aaaa\"", "false");
        assertOutput("puts isEmpty \"    \"", "false");
        assertOutput("puts isEmpty \"\"", "true");
    }

    @DisplayName("replace")
    @Test
    void testreplace() throws Exception {
        assertOutput("puts replace \"abbaa\" \"bb\" \"\"", "aaa");
    }

    @DisplayName("replaceAll")
    @Test
    void testreplaceAll() throws Exception {
        assertOutput("puts replaceAll \"abba booneym beatles pink parton\" \"a.*a\" \"-\"", "-rton");
    }

    @DisplayName("replaceFirst")
    @Test
    void testreplaceFirst() throws Exception {
        assertOutput("puts replaceFirst  \"abba booneym beatles pink parton\" \"a.*?a\" \"-\"", "- booneym beatles pink parton");
    }

    @DisplayName("substring")
    @Test
    void testsubstring() throws Exception {
        assertOutput("puts substring 1 5 \"abraka dabra\"", "brak");
    }    @DisplayName("substring")
    @Test
    void testStringIndexOf() throws Exception {
        assertOutput("""
                                puts indexOf "gigi" "hihi haha gigi gaga bakkara"
    """, "10");
    }

    @DisplayName("substring with '*'")
    @Test
    void testsubstring2() throws Exception {
        assertOutput("puts substring 1 * \"abraka dabra\"", "braka dabra");
    }

    @DisplayName("lc")
    @Test
    void testlc() throws Exception {
        assertOutput("puts lc \"AAAA\" 2", "aaaa");
    }

    @DisplayName("uc")
    @Test
    void testuc() throws Exception {
        assertOutput("puts uc \"aaaa\" 2", "AAAA");
    }

    @DisplayName("trim")
    @Test
    void testtrim() throws Exception {
        assertOutput("puts trim \"aaaa\"", "aaaa");
    }


    @DisplayName("create a list")
    @Test
    void testListCreate() throws Exception {
        assertOutput("set a list{} puts a", "[]");
        assertOutput("set a list{1 2 3} puts a", "[1, 2, 3]");
        // b gets evaluated as {1 2 3}, and that is 3, the last element
        assertOutput("set b quote{1 2 3} set a list b puts a", "[3]");
    }

    @DisplayName("fetch the first element of a list")
    @Test
    void fetchFirst() throws Exception {
        assertOutput("set a list{1 2 3} puts call a first", "1");
    }

    @DisplayName("fetch the n-tn element of a list")
    @Test
    void fetchNTh() throws Exception {
        assertOutput("set a list{1 2 3} puts call a get 1", "2");
    }

    @DisplayName("get the length of a list")
    @Test
    void length() throws Exception {
        assertOutput("set a list{1 2 3} puts call a length", "3");
    }

    @DisplayName("split a list")
    @Test
    void split() throws Exception {
        assertOutput("set a list{1 2 3 4} puts call a split 2", "[[1, 2], [3, 4]]");
    }

    @DisplayName("get the tail of a list")
    @Test
    void listRest() throws Exception {
        assertOutput("puts call list{1 2 3 4} rest", "[2, 3, 4]");
    }

    @DisplayName("split a list complex example")
    @Test
    void splitComplex() throws Exception {
        assertOutput("set a list{1 2 3 4} " +
                "setf a head '{" +
                "                set index shift " +
                "                set s call this split index " +
                "                call s first" +
                "             }" +
                "set b copy a " +
                "call b set 1 99 " +
                "puts a " +
                "puts b", "[1, 2, 3, 4][1, 99, 3, 4]");
    }

    @DisplayName("split a list complex example inserting into it")
    @Test
    void splitComplexInserting() throws Exception {
        assertOutput("set a list{1 2 3 4} " +
                "setf a head '{" +
                "                set index shift " +
                "                set s call this split index " +
                "                call s first" +
                "             }" +
                "set b copy a " +
                "call b insert 1 99 " +
                "puts a " +
                "puts b", "[1, 2, 3, 4][1, 99, 2, 3, 4]");
    }

    @DisplayName("Fetch the source")
    @Test
    void fetchTheSource() throws Exception {
        assertOutput("set a 3 " +
                "set b 1 " +
                // no matter how many {{{}}} .. source goes up to the first level where there is source
                "puts {set b 2 set a {{{{source}}}} " +
                "      puts a " +
                "      puts b}" +
                " puts b{} \"alma\"", " puts b{} \"alma\"221");
    }

    @DisplayName("modify the source")
    @Test
    void modifyTheSource() throws Exception {
        assertOutput("set a 3 " +
                "set b 1 " +
                "{sets \"puts \\\"Hello, World!\\\"\"} " +
                "puts a " +
                "puts b", "Hello, World!");
    }

    @DisplayName("modify the lexers")
    @Test
    void modifyLexers() throws Exception {
        assertOutput(
                "call $lex insert 3 '{" +
                        "if { eq charAt 0 source \"\\n\"}" +
                        "   {sets add \"{}\" substring 1 length source source}}" +
                        "set q add* 3 2 \n" +
                        "1 {} puts q", "5");

    }

    @DisplayName("modify the lexers after fixup")
    @Test
    void modifyLexersFixup() throws Exception {
        assertOutput(
                "fixup call $lex insert 3 '{" +
                        "if { eq charAt 0 source \"\\n\"}" +
                        "   {sets add \"{}\" substring 1 length source source}}" +
                        "set q add* 3 2 \n" +
                        "1 {} puts q", "6");

    }


    @DisplayName("test complex object execution")
    @Test
    void test() throws Exception {
        assertOutput("""
                set A object {}
                set B object {}
                setf B f "tacocat"
                setf A b B
                set C object A
                setf field C b f "gulash"
                puts field* A b f""", "gulash");
    }

    @DisplayName("test complex object execution")
    @Test
    void blockExecution() throws Exception {
        assertOutput("""
                { set A "Victor Noir"
                  setf $ A  "Yvan Salmon"
                  puts A }""", "Yvan Salmon");
    }


    @DisplayName("fetch token from source")
    @Test
    void fetchFromSource() throws Exception {
        assertOutput("""
                set fetch '{
                  while { $space } {}
                  switch
                  { setf $$ value string $keyword }{ value }
                  { setf $$ value string $string }{ value }
                  { setf $$ value string $number }{ value }
                  { setf $$ value string $symbol }{ value }
                  { setf $$ value string $block }{ value }
                  { setf $$ value string $blockClose }{ value }
                  {}
                }
                {
                puts fetch
                puts " "
                puts fetch
                puts " "
                puts fetch
                puts " "
                puts fetch
                puts " "
                puts fetch
                puts " "
                puts fetch
                }
                puts "a" 123 { 1 2 3 } [[ }
                
                """, "puts \"\"\"a\"\"\" 123L {1L 2L 3L} [[ }");
    }
    @DisplayName("switch case")
    @Test
    void testSwitch() throws Exception {
        assertOutput("""
                set a 1
                {switch
                {eq a 1} {puts 1}
                {eq a 2} {puts 2}
                {eq a 1} {puts 3}
                }""", "1");
    }
    @DisplayName("switch case null close")
    @Test
    void testSwitch2() throws Exception {
        assertOutput("""
                set a 1
                switch
                {eq a 1} {puts 1}
                {eq a 2} {puts 2}
                {eq a 1} {puts 3}
                {}""", "1");
    }

    @DisplayName("test operator metch   ")
    @Test
    void testMatch() throws Exception {
        assertOutput("""
                      set operator_mnemonic '{
                        set operators list {"==" "!=" "<" "<=" ">" ">=" "+" "-" "*" "/" "%"}
                        set mnemonics list {"eq" "ne" "lt" "le" "gt" "ge" "add" "sub" "mul" "div" "mod"}
                        set i 0
                        set m shift
                        while{ lt i call operators length } {
                          if { eq m  call operators get i }
                          { setf field $$ $$ m call mnemonics get i } {}
                          setf $$ i add i 1
                        }
                        m
                      }
                      puts operator_mnemonic "=="
                      puts " "
                        puts operator_mnemonic "!="
                        puts " "
                        puts operator_mnemonic "<"
                        puts " "
                        puts operator_mnemonic "<="
                        puts " "
                        puts operator_mnemonic ">"
                        puts " "
                        puts operator_mnemonic ">="
                        puts " "
                        puts operator_mnemonic "+"
                        puts " "
                        puts operator_mnemonic "-"
                        puts " "
                        puts operator_mnemonic "*"
                        puts " "
                        puts operator_mnemonic "/"
                        puts " "
                        puts operator_mnemonic "%" """, "eq ne lt le gt ge add sub mul div mod");
    }
    
}





