#include "configDevices.h"

int loadDevices(char *fileName, Device** result, int len)
{
    if(fileName == NULL) return -1;

    short flagErr = 0;
    int fileIn;
    if((fileIn = open(fileName,O_RDONLY)) < 0) flagErr = 1;

    int contDevice = 0;

    char line[MAX_FILE_LINE_LENGTH];
    short contLine = 0;
    static Device *device;
    while(!flagErr && contDevice < len && read(fileIn,&line[contLine],sizeof(char)) > 0)
    {
        if(line[contLine] == '\n')
        {
            line[contLine] = '\0';
            
            device = (Device*)(malloc(sizeof(Device)));
            device->status = 0;
            result[contDevice] = device;

            int offset = 0;

            for(int cont = 0, flagStop = 0; !flagErr && !flagStop; cont++)
            {
                if(cont < MAX_NAME_LENGTH && cont < contLine)
                {
                    if(line[cont] == FIELDS_SEPARATOR) { device->name[cont] = '\0'; flagStop = 1; offset = cont+1; }
                    else device->name[cont] = line[cont];
                }
                else flagErr = 1;
            }

            flagErr = flagErr || (offset >= contLine);

            char pin[10];
            int contPin = 0;
            if(!flagErr)
            {
                while(!flagErr && line[offset] != FIELDS_SEPARATOR)
                {
                    if(contPin < 10 - 1 && offset < contLine && (line[offset] >= '0' || line[offset] <= '9')) pin[contPin++] = line[offset++];
                    else flagErr = 1;
                }
            }
            if(!flagErr)
            {
                pin[contPin] = '\0';
                device->pin = atoi(pin);
            }

            flagErr = flagErr || (offset >= contLine);
            flagErr = flagErr || line[offset++] != FIELDS_SEPARATOR;
            flagErr = flagErr || (offset >= contLine);

            char def[2];
            int contDef = 0;
            if(!flagErr)
            {
                while(!flagErr && line[offset] != FIELDS_SEPARATOR)
                {
                    if(contDef < 2 - 1 && offset < contLine && (line[offset] == '0' || line[offset] == '1')) def[contDef++] = line[offset++];
                    else flagErr = 1;
                }
            }
            if(!flagErr)
            {
                def[contDef] = '\0';
                device->defaultStatus = atoi(def);
            }

            flagErr = flagErr || (offset >= contLine);
            flagErr = flagErr || line[offset++] != FIELDS_SEPARATOR;
            flagErr = flagErr || (offset >= contLine);

            char cha[2];
            int contCha = 0;
            if(!flagErr)
            {
                while(!flagErr && line[offset] != FIELDS_SEPARATOR)
                {
                    if(contCha < 2 - 1 && offset < contLine && (line[offset] == '0' || line[offset] == '1')) cha[contCha++] = line[offset++];
                    else flagErr = 1;
                }
            }
            if(!flagErr)
            {
                cha[contCha] = '\0';
                device->changeable = atoi(cha);
            }

            flagErr = flagErr || (offset >= contLine);
            flagErr = flagErr || line[offset++] != FIELDS_SEPARATOR;
            flagErr = flagErr || (offset >= contLine);

            char pulse[2];
            int contPulse = 0;
            if(!flagErr)
            {
                while(!flagErr && line[offset] != FIELDS_SEPARATOR)
                {
                    if(contPulse < 2 - 1 && offset < contLine && (line[offset] == '0' || line[offset] == '1')) pulse[contPulse++] = line[offset++];
                    else flagErr = 1;
                }
            }

            if(!flagErr)
            {
                pulse[contPulse] = '\0';
                device->pulse = atoi(pulse);
            }

            flagErr = flagErr || (offset >= contLine);
            flagErr = flagErr || line[offset++] != FIELDS_SEPARATOR;
            flagErr = flagErr || (offset >= contLine);

            device->contGroup = 0;

            for(int flagStop = 0; !flagErr && !flagStop && offset < contLine; device->contGroup++)
            {
                if(device->contGroup < MAX_GROUP_PER_DEVICE)
                {
                    for(int contGroupName = 0; !flagErr && !flagStop && offset < contLine; contGroupName++)
                    {
                        if(contGroupName < MAX_GROUP_NAME_LENGTH)
                        {
                            if(line[offset] == GROUPS_SEPARATOR) { device->groups[device->contGroup][contGroupName] = '\0'; flagStop = 1; }
                            else device->groups[device->contGroup][contGroupName] = line[offset];
                            offset++;
                        }
                        else if(contLine - offset > 0) flagErr = 1;
                    }

                    flagStop = 0;
                }
                else flagErr = 1;
            }
            
            contLine = 0;
            contDevice++;
        }  
        else contLine++; 
    }

    if(!flagErr) close(fileIn);
    else contDevice = -1;

    return contDevice;
}