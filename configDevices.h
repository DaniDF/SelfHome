#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

#define MAX_NAME_LENGTH 32
#define MAX_GROUP_NAME_LENGTH 32
#define MAX_GROUP_PER_DEVICE 8
#define MAX_GROUP_SIZE 100

#define FIELDS_SEPARATOR ';'
#define GROUPS_SEPARATOR ':'

//                                  32         SEP            32                      8                  ALL_SEP = 7
#define MAX_FILE_LINE_LENGTH (MAX_NAME_LENGTH + 1 + MAX_GROUP_NAME_LENGTH * MAX_GROUP_PER_DEVICE + (MAX_GROUP_PER_DEVICE - 1) + 1 + 1 + 1 + 1 + 1) //L'ultimo +1 serve a conteggiare il terminatore di stringa

#define MAX(x,y) (x>y)? x:y

typedef struct deviceStruct
{
    char name[MAX_NAME_LENGTH];
    char groups[MAX_GROUP_PER_DEVICE][MAX_GROUP_NAME_LENGTH];
    short contGroup;
    short pin;
    short defaultStatus;
    short changeable;
} Device;

typedef struct deviceStatusStruct
{
    Device *device;
    short status;
} DeviceStatus;

int loadDevices(char *fileName, Device** result, int len);

