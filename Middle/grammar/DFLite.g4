grammar DFLite;

program
    :   (c=command separator*)*   #programStatement
    ;

separator
    :   SPACE
    |   NL
    ;


//COMUNICATION_GRAMMAR

command
    :   'GET' SPACE+ arg=argument SPACE+ name=WORD                           #commandGet
    |   'SET' SPACE+ arg=argument SPACE+ name=WORD SPACE+ value=NUMBER       #commandSet
    |   'LST'                                            #commandLst
    ;

argument
    :   'BRIGH'   #argumentBri
    |   'STATE'   #argumentSta
    ;

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