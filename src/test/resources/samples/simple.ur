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
  puts "World!"
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

