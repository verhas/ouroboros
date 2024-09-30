"""
--------------------------------
Expression is:

expression ::=
  'not' expression |
  expression1 'or' expression |
  expression1 ;

expression1 ::=
  expression2 'and' expression |
  expression2 ;

expression2 ::=
  expression3 '==' expression2 |
  expression3 '!=' expression2 |
  expression3 '<'  expression2 |
  expression3 '<=' expression2 |
  expression3 '>'  expression2 |
  expression3 '>=' expression2 |
  expression3 ;

expression3 ::=
  expression4 '+' expression3 |
  expression4 '-' expression3 |
  expression4 ;

expression4 ::=
  expression5 '*' expression4 |
  expression5 '/' expression4 |
  expression5 '%' expression4 |
  expression5 ;

expression5 ::=
  ( expression )   |
  constant         |
  variable         |
  '+' expression4  |
  '-' expression4  ;

snippet xpression.ur """
"""
Fetch the next token and store it in the global variable '$nextToken'.
It also stores the state of the source in the global variable $source as it was before the fetch in case the caller
needs to restore.
"""
set fetch '{
  set value {}
  while { $space } {}
  set src string source
  set f '{ setg $source src setg $nextToken value }
  set g '{ setn value string shift }
  switch
  { g $keyword    } {f}
  { g $string     } {f}
  { g $number     } {f}
  { g eSymbol     } {f}
  { g $symbol     } {f}
  { g $block      } {f}
  { g $blockClose } {f}
  {}
}

set eSymbol '{
  if { ne -1 indexOf at source 0 "(+-*/%)" }
     {
       set returnValue at source 0
       sets substring 1 * source
       returnValue
     }
     {}
}

set to_mnemonic '{
  set operators list {"==" "!=" "<" "<=" ">" ">=" "+" "-" "*" "/" "%" "&&" "||" "!"}
  set mnemonics list {"eq" "ne" "lt" "le" "gt" "ge" "add" "sub" "mul" "div" "mod" "and" "or" "not"}
  set i 0
  set m shift
  while{ lt i length operators } {
    if { eq m  at operators i }
    { setf field $$ $$ m at mnemonics i } {}
    setf $$ i add i 1
  }
  m
}

"""
Analyze an expression. The first token is assumed to be prefetched and in the global variable $nextToken.
The return value of the function is the value of the expression transformed to UR and the global variable $nextToken
contains the first unprocessed token.
"""
set expression '{
  if { eq $nextToken "not" }{
    fetch
    add "not " expression
    }{
    set left expression1
    while { eq $nextToken "or" }{
      set operator $nextToken
      fetch
      set right expression
      setf $$ left add* {to_mnemonic operator} " " left " " right {}
      }
    left
    }
  }

set expression1 '{
  set left expression2
  while { eq $nextToken "and" }{
    set operator $nextToken
    fetch
    set right expression2
    setf $$ left add* {to_mnemonic operator} " " left " " right {}
    }
  left
  }

set expression2 '{
  set left expression3
  while { ne -1 indexOf $nextToken "== != < <= > >=" }{
    set operator $nextToken
    fetch
    set right expression2
    setf $$ left add* {to_mnemonic operator} " " left " " right {}
    }
  left
  }

set expression3 '{
  set left expression4
  { """Correct the lexical analysis, because at this point a '+' or a '-' can be a binary operator instead of
       the sign of a number. If the first character is '+' or '-' then we have to undo the parsing of the number
       using the saved $source state and use the first character as the token.
    """
    set firstChar at $source 0
    if{ ne -1 indexOf firstChar "+-" }{
      """skip the first character as it will not be part of the rest,
         even if it is a number and put back the already parsed
         source w/o the + or -"""
      sets substring 1 * $source
      setg $nextToken firstChar
      }
    }
  while{ ne -1 indexOf $nextToken "+ -" }{
    set operator $nextToken
    fetch
    set right expression4
    setf $$ left add* {to_mnemonic operator} " " left " " right {}
    }
  left
  }

set expression4 '{
  set left expression5
  while{ ne -1 indexOf $nextToken "* / %" }{
    set operator $nextToken
    fetch
    set right expression5
    setf $$ left  add* {to_mnemonic operator} " " left " " right {}
    }
  left
  }

set expression5 '{
  switch
    { eq $nextToken "(" }{
      fetch
      set left expression
      if { ne $nextToken ")" }
         { error add "Expected ')' instead of " $nextToken }
         { fetch left }
      }
   { eq $nextToken "+" }{
     fetch
     expression4
     }
   { eq $nextToken "-" }{
     fetch
     add "0 -" expression5
     }
   { true }{
     set left $nextToken
     fetch left
     }
   {}
}

insert $lex 0 '{
  if { eq at source 0 "("}
     {
       fetch "get over the opening parenthesis"
       fetch "get the first token of the expression"
       set result expression
       if { ne $nextToken ")" }
          { error add "Expected ')' instead of " $nextToken }
          {}
       sets add result source
       {}
     }
}

puts ((6+2)*3 % 7)
"""
end snippet
--------------------------------
"""
