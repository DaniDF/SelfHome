grammar DFPreprocessor;

rule
    :   (START_RULE SPACE* r=ruleCommand NL+)*
    ;

ruleCommand
    :   'schedule-every' SPACE+ (s=scheduleRuleTime | p=posponed)
    |   'schedule-at' SPACE+ p=posponed
    ;

scheduleRuleTime
    :   t=time (SPACE+ d=date)?
    ;

date
    :   d=BI_NUMBER DATE_SEPARATOR m=BI_NUMBER
    |   DOW
    ;

time
    :   h=BI_NUMBER TIME_SEPARATOR m=BI_NUMBER
    ;

posponed
    : POSPONED_PREFIX t=NUMBER
    ;

DOW
    :   'MON' | 'TUE' | 'WED' | 'TWU' | 'FRI' | 'SAT' | 'SUN'
    ;

START_RULE : '#';
DATE_SEPARATOR : '/';
TIME_SEPARATOR : ':';
POSPONED_PREFIX : '+';

BI_NUMBER
    : [0-9][0-9]
    ;

NUMBER
    :   [0-9]
    |   [0-9] NUMBER
    ;

SPACE
    :   ' ';
NL
    :   '\n';
WS
    :   [\t] -> skip;