grammar DFLite;

program
    :   ((s=statement|l=listCommand) separator*)*   #programStatement
    ;

statement
    :   l=lineCommand    #statementLineCommand
    |   'repeat' SPACE* ROUND_BRACKET_OP SPACE* f=filter SPACE* ROUND_BRACKET_CL separator* b=block  #statementRepeat
    |   i=ifStatement   #statementIf
    ;

ifStatement
    :   'if' SPACE* ROUND_BRACKET_OP SPACE* f=filter SPACE* ROUND_BRACKET_CL separator*
        b=block (separator* 'else' separator* (i=ifStatement | b2=block))?  #ifStatementIf
    ;

block
    :   GRAPH_BRACKET_OP separator* (sta=statement separator*)* GRAPH_BRACKET_CL  #blockStatement
    ;

filter
    :   c=logicOperatorOr    #filterComp
    ;

lineCommand
    :   c=logicOperatorOr END_COMMAND   #lineCom
    ;

separator
    :   SPACE
    |   NL
    ;

//EXPRESSION GRAMMAR

logicOperatorOr
    :   l=logicOperatorAnd  #logicLoginAnd
    |   ls=logicOperatorOr LOGIC_OR ld=logicOperatorAnd #logicOr
    ;

logicOperatorAnd
    :   c=comparator  #logicComparator
    |   l=logicOperatorAnd LOGIC_AND c=comparator   #logicAnd
    ;

comparator
    :   s=sum   #comparatorSum
    |   c=comparator SPACE* op=COMP_LT SPACE* s=sum #comparatorLT
    |   c=comparator SPACE* op=COMP_GT SPACE* s=sum #comparatorGT
    |   c=comparator SPACE* op=COMP_LE SPACE* s=sum #comparatorLE
    |   c=comparator SPACE* op=COMP_GE SPACE* s=sum #comparatorGE
    |   c=comparator SPACE* op=COMP_EE SPACE* s=sum #comparatorEE
    |   'isOneOfThese[' SPACE* sel=TIMESELECTOR SPACE* ',' SPACE* val=NUMBER SPACE* ']'  #comparatorTime
    ;

sum
    :   a=mulDiv    #sumMulDiv
    |   f=sum SPACE* op=SUM SPACE* s=mulDiv #sumAdd
    |   f=sum SPACE* op=SUB SPACE* s=mulDiv #sumMinu
    ;

mulDiv
    :   t=term  #mulDivTerm
    |   f=mulDiv SPACE* op=MUL SPACE* s=term    #mulDivMul
    |   f=mulDiv SPACE* op=DIV SPACE* s=term    #mulDivDiv
    ;

term
    :   i=item  #termItem
    |   NOT i=term  #termNotTerm
    |   ROUND_BRACKET_OP SPACE* ex=logicOperatorOr SPACE* ROUND_BRACKET_CL    #termBra
    ;

item
    :   i=NUMBER      #itemNumber
    |   c=command     #itemCommand
    ;


//COMUNICATION_GRAMMAR

command
    :   'GET' SPACE+ sel=SEL SPACE+ name=WORD                           #commandGet
    |   'SET' SPACE+ sel=SEL SPACE+ name=WORD SPACE+ value=term         #commandSet
    |   'TGL' SPACE+ sel=SEL SPACE+ name=WORD                           #commandToggle
    |   'NGR' SPACE+ name=WORD                                          #commandNewGroup
    |   'DGR' SPACE+ name=WORD                                          #commandDelGroup
    |   'ADG' SPACE+ nameD=WORD SPACE+ nameG=WORD                       #commandAddDeviceToGroup
    |   'DDG' SPACE+ nameD=WORD SPACE+ nameG=WORD                       #commandDelDeviceFromGroup
    |   'sleep' SPACE+ value=term                                       #commandSleep
    ;

listCommand
    :   'LST'                                                           #commandList
    |   'LGR'                                                           #commandListGroup
    |   'LDG' SPACE+ name=WORD                                          #commandListDeviceInGroup
    ;

TIMESELECTOR : 'YEAR' | 'MONTH' | 'DAY' | 'DOW' | 'HOUR' | 'MIN' | 'SEC' | 'MILS';

SEL :   'DISP' | 'GRUP';

END_COMMAND : ';';
GRAPH_BRACKET_OP : '{';
GRAPH_BRACKET_CL : '}';

ROUND_BRACKET_OP : '(';
ROUND_BRACKET_CL : ')';

NOT : '!';

SUM : '+';
SUB : '-';
MUL : '*';
DIV : '/';

LOGIC_OR : '||';
LOGIC_AND : '&&';

COMP_LT : '<';
COMP_GT : '>';
COMP_LE : '<=';
COMP_GE : '>=';
COMP_EE : '==';

NUMBER
    :   [0-9]
    |   [0-9] NUMBER
    ;
WORD
    :   '<'([a-z] | [A-Z] | [0-9] | SPACE)+'>'
    |   ([a-z] | [A-Z] | [0-9])+;

SPACE
    :   ' ';
NL
    :   '\n';

WS
    :   [\t] -> skip;