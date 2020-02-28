#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "configDefines.h"

#define MAX(x,y) (x>y)? x:y

typedef struct deviceStruct
{
    char name[MAX_NAME_LENGTH];
    char groups[MAX_GROUP_PER_DEVICE][MAX_GROUP_NAME_LENGTH];
    short contGroup;
    short pin;
    short defaultStatus;
    short changeable;
    short pulse;
    short status;
} Device;

int loadDevices(char *fileName, Device** result, int len);

