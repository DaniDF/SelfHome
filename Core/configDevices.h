#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

#ifndef MAX_DEVICE_NAME_LENGTH
    #define MAX_DEVICE_NAME_LENGTH 32
#endif

#ifndef MAX_PIN_DIGIT
    #define MAX_PIN_DIGIT 5
#endif

#ifndef MAX_PIN_DEFAULT
    #define MAX_PIN_DEFAULT 3
#endif

#ifndef FIELDS_SEPARATOR
    #define FIELDS_SEPARATOR ';'
#endif

#ifndef DEVICE_SEPARATOR
    #define DEVICE_SEPARATOR '\n'
#endif

#ifndef MAX_FILE_LINE_LENGTH        //Nome_disp          sep  pin_digit        sep      def_sta            sep  chang   sep     pulse
    #define MAX_FILE_LINE_LENGTH MAX_DEVICE_NAME_LENGTH + 1 + MAX_PIN_DIGIT +   1 +   MAX_PIN_DEFAULT +     1 +   1 +    1 +      1
#endif

typedef struct deviceStruct
{
    char name[MAX_DEVICE_NAME_LENGTH];
    short pin;
    short defaultStatus;
    short changeable;
    short pulse;
    long status;
} Device;

int loadDevices(char *fileName, Device** result, int len);

