#include <string.h>
#include "configDevices.h"

int loadDevices(char *fileName, Device** result, int len)
{
    if(fileName == NULL) return -1;

    char flagErr = 0;
    int fileIn;
    if((fileIn = open(fileName,O_RDONLY)) < 0) flagErr = -1;

    int countDevice = 0;

    char line[MAX_FILE_LINE_LENGTH + 1];
    short countLine = 0;
    static Device *device;
    while(!flagErr && countDevice < len && read(fileIn,&line[countLine],sizeof(char)) > 0)
    {
        if(line[countLine] != DEVICE_SEPARATOR)
        {
            if(line[countLine] != '\n' && line[countLine] != '\r' && line[countLine] != '\t') countLine++;
        }
        else
        {
            line[countLine] = '\0';
            countLine = -1;
            device = (Device*)(malloc(sizeof(Device)));

            short countDeviceName = 0;

            while(!flagErr && countDeviceName < MAX_DEVICE_NAME_LENGTH - 1 && line[++countLine] != FIELDS_SEPARATOR)
            {
                device->name[countDeviceName++] = line[countLine];
            }
            device->name[countDeviceName] = '\0';


            char pinDigit[MAX_PIN_DIGIT + 1];
            short countPinDigit = 0;

            while(!flagErr && line[++countLine] != FIELDS_SEPARATOR && countPinDigit < MAX_PIN_DIGIT)
            {
                if(line[countLine] == ' ') ;
                else if(line[countLine] < '0' || line[countLine] > '9') flagErr = 1;
                else pinDigit[countPinDigit++] = line[countLine];
            }
            pinDigit[countPinDigit] = '\0';
            if(!flagErr) device->pin = atoi(pinDigit);

            char defaultDigit[MAX_PIN_DEFAULT + 1];
            short countDefaultDigit = 0;

            while(!flagErr && line[++countLine] != FIELDS_SEPARATOR && countDefaultDigit < MAX_PIN_DEFAULT)
            {
                if(line[countLine] == ' ') ;
                else if(line[countLine] < '0' || line[countLine] > '9') flagErr = 1;
                else defaultDigit[countDefaultDigit++] = line[countLine];
            }
            defaultDigit[countDefaultDigit] = '\0';
            if(!flagErr)
            {
                device->defaultStatus = atoi(defaultDigit);
                device->status = device->defaultStatus;
            }

            char changeable[2];
            changeable[0] = '\0';
            changeable[1] = '\0';

            while (!flagErr && line[++countLine] != FIELDS_SEPARATOR)
            {
                if(line[countLine] == ' ') ;
                else if(line[countLine] < '0' || line[countLine] > '9' || changeable[0] != '\0') flagErr = 1;
                else changeable[0] = line[countLine];
            }
            if(!flagErr) device->changeable = atoi(changeable);
            
            char pulse[2];
            pulse[0] = '\0';
            pulse[1] = '\0';

            while (!flagErr && line[++countLine] != FIELDS_SEPARATOR && line[countLine] != '\0')
            {
                if(line[countLine] == ' ') ;
                else if(line[countLine] < '0' || line[countLine] > '9' || pulse[0] != '\0') flagErr = 1;
                else pulse[0] = line[countLine];
            }
            if(!flagErr) device->pulse = atoi(pulse);
            

            countLine = 0;

            result[countDevice++] = device;
        }
    }

    if(flagErr != -1) close(fileIn);
    if(flagErr) countDevice = -1;

    return countDevice;
}