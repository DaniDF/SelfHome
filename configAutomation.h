#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include "configDefines.h"

typedef struct automationStruct
{
    char type[5];
    char name[MAX_NAME_LENGTH];
    short startDays[7];
    short startHour;
    short startMinute;
    short startSec;
    short stopDays[7];
    short stopHour;
    short stopMinute;
    short stopSec;
} Automation;

int loadAutomations(char *dirName,Automation** automations,int len);
int storeAutomations(char *dirName,Automation** automations,int len);
