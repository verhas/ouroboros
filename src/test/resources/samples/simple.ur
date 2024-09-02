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
snippet unquoted.ur
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
call $lex insert 0 '{
if { eq charAt 0 source "\n"}
   {sets add "{}" substring 1 length source source}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet fixup.ur
fixup call $lex insert 0 '{
if { eq charAt 0 source "\n"}
   {sets add "{}" substring 1 length source source}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet nofixup.ur
call $lex insert 0 '{
if { eq charAt 0 source "\n"}
   {sets add "{}" substring 1 length source source}}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------
snippet fixup_explanation.ur
puts """
The sample `fixup.ur` first executes the `fixup` command.
The command then reads all the remaiing source code as input and replaces the input string with the list of the tokens.
After that the program inserts a new lexical analyser into the list `$lex` at the beginning.
This lexical analyser is NEVER invoked since the code is already tokenized.
Because of that the addition command presented as `add*`, which means it will read all the arguments until the end of the block, or an `{}` is found will add `3`, `2`, and `1`.

In the second case there is no `fixup`.
The syntax analyser fetches only as far from the input source string as needed and no more.
First the command `call` is identified by the built-in lexical analyser as a bare word and since it is a command it will be executed.
The command `call` will fetch the next two arguments to get the object and the method.
When it fetches the arguments the interpreter invokes the lexical analysers to get the arguments.
The rest of the input is not tokenized and is available as a string including and following the space before `0 '{`.
The arguments for the call are are `$lex` and `insert`.
Invoking `insert` on the list object advances the lexical analysis further.
The next argument is the position to insert a new value in the list, this is `0`.
The element to insert is a quoted block, which will be also tokenized and as a block command gets inserted into the list.

At this point the source code string contains the part that starts with `set q add* ...`.
As the execution advances it reads on but this time -- without detailing the intermediate steps -- it will also use the inserted lexical analyser.
When this analyser sees the new line it will replace it with an empty block in the source.
That was the source will be transformed on the fly to `set q add* 3 2 {}`.
The commands `1` and `{}` are executed and ignored.
Finally the result of the addition is printed.
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
