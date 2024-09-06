--------------------------------
Expression is:

expression ::=
  'not' ( expression ) |
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
set fetch '{
  while { $space } {}
  if { setf $$ value string $keyword }{ value }{
  if { setf $$ value string $string }{ value }{
  if { setf $$ value string $number }{ value }{
  if { setf $$ value string $symbol }{ value }{
  if { setf $$ value string $block }{ value }{
  if { setf $$ value string $blockClose }{ value }{"baj"
  }}}}}}
}
set expression '{
   set token fetch
   if { eq token "not" }
      {
        if{ eq fetch "(" }
          { set value add "not " expression
            if { eq fetch ")" }
               { value }
               { error "Expected ) after not" }
          }
          {error "Expected ( after not" }
      }
      { set left expression1
        if { startsWith source "and" }
          { sets substring 3 * source
            while {startsWith source " "}
              { sets substring 1 * source }
            set right expression
            add* "and " left " " right {}
          }
          { set left expression1
            if { startsWith source "or" }
              { sets substring 2 * source
                while {startsWith source " "}
                  { sets substring 1 * source }
                set right expression
                add "or " left right
              }
              { left }
          }
      }

}


call $lex insert 0 '{
  if { eq charAt 0 source "("}
       {sets substring 1 * source}
     }
}
set q add* 3 2
1 {} puts q
end snippet
--------------------------------