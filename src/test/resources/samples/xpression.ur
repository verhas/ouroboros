--------------------------------
Expression is:

expression ::=
  'not' expression |
  expression1 'and' expression |
  expression1 'or' expression |
  expression1 ;

expression1 ::=
  expression2 '==' expression1 |
  expression2 '!=' expression1 |
  expression2 '<' expression1  |
  expression2 '<=' expression1 |
  expression2 '>' expression1  |
  expression2 '>=' expression1 |
  expression2 ;

expression2 ::=
  expression3 '+' expression2 |
  expression3 '-' expression2 |
  expression3 ;

expression3 ::=
  expression4 '*' expression3 |
  expression4 '/' expression3 |
  expression4 '%' expression3 |
  expression4 ;

expression4 ::=
  ( expression )   |
  constant         |
  variable         |
  '+' expression4  |
  '-' expression4  ;

snippet xpression.ur
"""
Fetch the next token and store it in the global variable '$nextToken'.
It also stores the state of the source in the global variable $source as it was before the fetch in case the caller
needs to restore.
"""
set fetch '{
  while { $space } {}
  set src source
  if { setf $$ value string $keyword    }{ setg $source src setg $nextToken value }{
  if { setf $$ value string $string     }{ setg $source src setg $nextToken value }{
  if { setf $$ value string $number     }{ setg $source src setg $nextToken value }{
  if { setf $$ value string $symbol     }{ setg $source src setg $nextToken value }{
  if { setf $$ value string $block      }{ setg $source src setg $nextToken value }{
  if { setf $$ value string $blockClose }{ setg $source src setg $nextToken value }{
  }}}}}}
}
"""
Analyze an expression. The first token is assumed to be prefetched and in the global variable $nextToken.
The return value of the function is the value of the expression transformed to UR and the global variable $nextToken
contains the first unprocessed token.
"""
set expression '{
   if { eq $nextToken "not" }
      {
        fetch
        add "not " expression
      }
      { set left expression1
        switch
        { eq $nextToken "and" }
           {
            set right expression
            add* "and " left " " right {}
           }
        { eq $nextToken "or" }
              {
              set right expression
              add* "or " left " " right {}
              }
        { true }
              { left }
      }
}


end snippet
--------------------------------