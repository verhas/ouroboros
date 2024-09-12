This file contains sample programs executed by TestRunSamples.java in the src/test/java directory.
The file is also collected by the README.adoc.jam file to generate the documentation.

The output of each program is saved to the file target/generated-output/XXX where XXX is the name of the snippet.

--------------------------------
snippet puts.ur
puts "Hello, World!"
end snippet
--------------------------------
snippet putsadd.ur
puts add "Hello, " "World!"
puts "\n"
puts add 2 2
puts "\n"
puts add "2" 2
puts "\n"
puts add BigDecimal "2222222222222222222222222222" 2
puts "\n"
puts add 2.3 5
end snippet
--------------------------------
snippet putsdiv.ur
puts div 55 13
puts "\n"
puts div 55.0 13
puts "\n"
puts div BigDecimal 10 3
puts "\n"
set $scale 10
set $round "CEILING"
puts div BigDecimal 10 3
end snippet
--------------------------------
snippet block.ur
puts {
  puts "Hello, "
  "World!"
}
end snippet
--------------------------------
snippet set.ur
set a "Hello, "
{
  puts a
  puts "\n"
  set a "World!"
  puts a
  puts "\n"
  setg a "Gruetzi, "
  puts a
  puts "\n"
}
puts a
end snippet
--------------------------------
snippet object.ur
set obj object {}
setf obj name "Hello, "
{
  puts field obj name
  puts "World!"
}
end snippet
--------------------------------
snippet err_unquoted.ur
set a { puts shift }
a "Hello, World!"
end snippet
--------------------------------
snippet quoted.ur
set a '{ puts shift }
a "Hello, World!"
end snippet
--------------------------------
snippet method.ur
set a object{}
setf a b '{puts shift puts field this name puts "!"}
setf a name "World"
call a b "Hello, "
end snippet
--------------------------------
snippet metho1.ur
set a object{}
set g '{puts shift puts field this name puts "!"}
setf a b 'g
puts field a b
setf a name "World"
call a b "Hello, "
end snippet
--------------------------------
snippet lexNewLine.ur
insert $lex 0 '{
if { eq at source 0 "\n"}
   {sets substring 1 length source source '{}}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet fixup.ur
fixup insert $lex 0 '{
if { eq at source 0 "\n"}
   {sets add "{}" substring 1 length source source}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet nofixup.ur
insert $lex 0 '{
if { eq at source 0 "\n"}
   {sets add "{}" substring 1 length source source}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet fixup_explanation.ur
puts """
The sample `fixup.ur` first executes the `fixup` command.
The command then reads all the remaining source code as input and replaces the input string with a list of tokens.
After that, the program inserts a new lexical analyzer into the list `$lex` at the beginning.
This lexical analyzer is NEVER invoked since the code is already tokenized.
Because of that, the addition command presented as `add*`, which means it will read all the arguments until the end of the block, or until `{}` is found, and will add `3`, `2`, and `1`.

In the second case, there is no `fixup`.
The syntax analyzer fetches only as much from the input source string as needed and no more.
First, the command `call` is identified by the built-in lexical analyzer as a bare word, and since it is a command, it will be executed.
The command `call` will fetch the next two arguments to get the object and the method.
When it fetches the arguments, the interpreter invokes the lexical analyzers to get the arguments.
The rest of the input is not tokenized and is available as a string, including and following the space before `0 '{`.
The arguments for the call are `$lex` and `insert`.
Invoking `insert` on the list object advances the lexical analysis further.
The next argument is the position to insert a new value in the list; this is `0`.
The element to insert is a quoted block, which will also be tokenized and, as a block command, gets inserted into the list.
The block does not execute at this point.

Now, the source code string contains the part that starts with `set q add* ...`.
As the execution advances, it reads on, but this time -- without detailing the intermediate steps -- it will also use the inserted lexical analyzer.
When this analyzer encounters the new line, it will replace it with an empty block in the source.
That way, the source will be transformed on the fly to `set q add* 3 2 {} 1 {} ...`.
The addition is performed and the value is assigned to the variable `q`.
The commands `1` and `{}` are executed and ignored.
Finally, the result of the addition is printed.

"""
end snippet
"""
end snippet
--------------------------------
snippet call.ur
set obj object{}
setf obj method '{
   puts field this greeting
   puts shift
}
setf obj greeting "Hello, "
call obj method "World!"
end snippet
--------------------------------
snippet copy.ur
set A object{}
setf A f1 "racecar"
setf A f2 "tacocat"
set B 'A
set C copy A
setf B f1 "trabant"
setf C f2 "gulash"
puts field A f1
puts "\n"
puts field A f2
end snippet
--------------------------------
snippet copy_explanation.ur
puts """
The program creates an object `A` with two fields `f1` and `f2`.
Then it creates a block `B` that references the object `A`.
The program also creates a copy of the object `A` and assigns it to the variable `C`.
After that, it sets the field `f1` of the object `B` to `"trabant"` and the field `f2` of the object `C` to `"gulash"`.
Finally, it prints the values of the fields `f1` and `f2` of the object `A`.
Since the variable `B` references the object `A`, the field `f1` of the object `A` is changed to `"trabant"`.
On the other hands, when we change the field `f2` of the object `C`, the object `A` remains unchanged because the variable `C` is a copy of the object `A`.
"""
end snippet
--------------------------------
snippet eval.ur
eval puts """{
    puts "Hello, "
    puts "World!"
  }
  "will print out"
"""
end snippet
--------------------------------
snippet field.ur
set A object {}
setf A f1 "racecar"
setf A f2 "tacocat"
puts field A f1
puts "\n"
puts field A f2
end snippet
--------------------------------
snippet if.ur
set i 0
while { lt i 10 } {
  if { mod i 2 } {
    puts i
  }{}
  setg i add i 1
}
end snippet
--------------------------------
snippet list.ur
set i list {1 2 3 4 5}
puts i
end snippet
--------------------------------
snippet list_first.ur
set i list {0 1 2 3 4 5}
puts first i
end snippet
--------------------------------
snippet list_get.ur
set i list {0 1 2 3 4 5}
puts at i 1
end snippet
--------------------------------
snippet list_insert.ur
set i list {0 1 2 3 4 5}
insert i 1 99
puts i
end snippet
--------------------------------
snippet list_last.ur
set i list {0 1 2 3 4 5}
puts last i
end snippet
--------------------------------
snippet list_length.ur
set i list {0 1 2 3 4 5}
puts length i
end snippet
--------------------------------
snippet list_rest.ur
set i list {0 1 2 3 4 5}
puts rest i
end snippet
--------------------------------
snippet list_set.ur
set i list {0 1 2 3 4 5}
setl i 1 99
puts i
end snippet
--------------------------------
snippet list_split.ur
set i list {0 1 2 3 4 5}
puts split i 2
end snippet
--------------------------------
snippet object_complex.ur
set A object {}
set B object {}
setf B f "tacocat"
setf A b B
set C object A
setf field C b f "gulash"
puts field* A b f {}
setf field C b object {}
puts add "\n" field* A b f {}
end snippet
--------------------------------
snippet object_complex_explanation.ur
puts """
We create two objects `A` and `B`.
The object `B` has a field `f` with the value `"tacocat"`.
The object `A` has a field `b` that references the object `B`.
Then we create an object `C` that inherits the fields of `A`.
If we set the field `f` of the object `B` to `"gulash"` through the object `C`, the field `f` of the object `B` will be changed to `"gulash"`.
It is the same and only instance of `B`.

Now if we set the field `b` of the object `C` to an empty object, the field `b` of object `A` does not change.
`C` only inherits the fields from `A` and the change there does not affect `A`.
"""
end snippet
--------------------------------
snippet quote.ur
set hi quote { puts shift puts shift}
hi "Hello, " "World!"
end snippet
--------------------------------
snippet curry.ur
set hi quote { puts shift puts shift}
hi "Hello, " "World!"
set gruezi quote{ hi "Gruetzi, " shift }
set world '{hi shift "World!" }
puts "\n"
gruezi "Reto!"
puts "\n"
world "Ciao, "
end snippet
--------------------------------
snippet set1.ur
set A "Hello "
{ puts A set A "World!" } puts A
puts "\n"
set A "Hello "
{ puts A setg A "Gruetzi" } puts A
puts "\n"
setf $ A "Hello "
{ puts A setf $$ A "Ciao" } puts A
end snippet
--------------------------------
snippet set1_explanation.ur
puts """
The program sets the variable `A` to `"Hello "`.
Then it prints the value of `A` and sets `A` to `"World!"`.
This setting, however, is only valid within the block.
After the block, the value of `A` is still `"Hello "`.

In the next section we set `A` to `"Hello "` again.
This time we use the `setg` command to set the global variable `A` to `"Gruetzi"`.
After the block, the value of `A` is `"Gruetzi"`.

Tne next section sets the variable `A` as a field of the object `$`.
This variable is available in all environments and it is the object that has all the variables on that level as fields.
Then it prints the value of `A` and sets the variable `A` to `"Ciao"`.
This time it sets this variable as the field of the object `$$`.
This variable is only available in side blocks and they represent the object that has all the variables on the enclosing level as fields.
"""
end snippet
--------------------------------
snippet setn1.ur
set A "Hello, "
puts {
    puts A
    setn A "Gruetzi, "
    set A "World!"
    setn A "Welt!"
}
puts A
end snippet
--------------------------------
snippet setn1_explanation.ur
puts """
The program sets the global variable `A` to `"Hello, "`.
Then it prints the value of `A` and sets `A` to `"Gruetzi, "`.
This time it sets the global variable `A` to `"Gruetzi, "` calling the `setn` command.
The next command creates a local variable `A` and sets it to `"World!"`.
The following `setn` command sets this local variable `A` to `"Welt!"`.
Since it is returned and it is the last value in the block it is printed.

After the block, the global variable `A` is still `"Gruetzi, "`.
"""
end snippet
--------------------------------
snippet binop.ur
"simple binary operation adding 2 and 2:"
puts add 2 2
"multiple addition till there is a closing {}:"
puts add* 2 2 2 2 {}
"multiple addition till there are no more tokens to fetch on the given level to add:"
puts {add* 2 2 2 2}
end snippet
--------------------------------
snippet setf1.ur
set A object{}
setf A f1 "racecar"
puts field A f1
end snippet
--------------------------------
snippet setf2.ur
  { set A "Victor Noir"
  setf $ A  "Yvan Salmon"
  puts A }
end snippet
--------------------------------
snippet setf3.ur
  { set A "Victor Noir\n"
     { set A  "Yvan Salmon\n"
       { set A "Bonaparte\n"
         setf $$ A "Napoleon\n"
         setf field $$ $$ A "Josephine\n"
         puts A
       }
       puts A
     }
  puts A
  }
end snippet
--------------------------------
snippet setf4.ur
set A object{}
setf A f1 ' { "racecar" }
puts field A f1
puts "\n"
puts call A f1
end snippet
--------------------------------
snippet switch1.ur
set a 1
switch
{eq a 1} {puts 1}
{eq a 2} {puts 2}
{eq a 1} {puts 3}
{}
end snippet
--------------------------------
snippet switch2.ur
set a 1
{switch
 {eq a 1} {puts 1}
 {eq a 2} {puts 2}
 {eq a 1} {puts 3}
}
end snippet
--------------------------------
snippet stackTrace.ur
set stack {}
set stackTrace '{
  setn stack field? $$ $$ "whoever was calling this function"
  set i 1 "only to count the stack frames"
  while{ stack }{
    puts add* " " i ": " field? stack $it "\n" {}
    setn stack field? stack $$
    setn i add i 1
  }
}
set fun4 '{ stackTrace }
set fun3 '{       fun4 }
set fun2 '{       fun3 }
set fun1 '{       fun2 }
fun1
end snippet
--------------------------------
snippet simple_closure.ur
{
  set localVariable "Hello, World!"
  setg f closure ' { puts localVariable }
}
f
end snippet
--------------------------------
snippet high_order_func.ur
set greet quote {
   set z shift
   closure '{puts add "Hello " z }
   }
set ga greet "Agnieszka"
set gb greet "Bartek"
ga
puts "\n"
gb
end snippet
--------------------------------
snippet redefine_puts.ur
set puts closure quote {puts "--" puts shift puts "--\n" }
puts "Hello, World!"
puts "Hello, Ouroboros!"
end snippet
--------------------------------
snippet closure_quote.ur
set ` '{ closure arg }
set puts `{puts "--" puts shift puts "--\n" }
puts "Hello, World!"
puts "Hello, Ouroboros!"
end snippet
--------------------------------
