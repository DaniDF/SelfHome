#include "configAutomation.h"

int loadSingleFileAutomation(char *fileName,Automation** automation, int start, int len);

int loadAutomations(char *dirName,Automation** automations,int len)
{
    int result = 0;
    int flagErr = 0;
    DIR * dirIn = opendir(dirName);
    if(dirIn == NULL) flagErr = 1;

    flagErr = flagErr || chdir(dirName) < 0;

    static Automation *automation;

    struct dirent * fileDir;
    while(!flagErr && result+1 < len && (fileDir = readdir(dirIn)) != NULL)
    {
        if(fileDir->d_type == DT_REG)
        {
            flagErr = flagErr || strlen(fileDir->d_name) < 5;
            int par;
            flagErr = flagErr || ((par = loadSingleFileAutomation(fileDir->d_name,automations,result,len)) < 0);

            if(!flagErr) result += par;
        }
    }

    closedir(dirIn);

    flagErr = flagErr || chdir("..") < 0;

    return (flagErr)? -1:result;
}

int loadSingleFileAutomation(char *fileName,Automation** automations, int start, int len)
{
    int result = 0;
    int flagErr = 0;

    int fileIn = open(fileName,O_RDONLY);
    if(fileIn < 0) flagErr = 1;

    static Automation* automation;
    automation = (Automation*)(malloc(sizeof(Automation)));

    char car;
    char contCar = 0;
    char temp[3];

    while(!flagErr && start+1 < len && (read(fileIn,&car,sizeof(char)) > 0))
    {
        if(contCar >= 0 && contCar < 7)
        {
            if(car < '0' || car > '1') flagErr = 1;
            else automation->startDays[contCar] = car - '0';
        }
        else if(contCar == 7 && car != AUTO_FIELDS_SEPARATOR) flagErr = 1;
        else if(contCar >= 8 && contCar < 10)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-8] = car;
        }
        else if(contCar == 10)
        {
            if(car != ':') flagErr = 1;
            else
            {
                temp[2] = '\0';
                automation->startHour = atoi(temp);
                if(automation->startHour < 0 || automation->startHour > 23) flagErr = 1;
            }
        }
        else if(contCar >= 11 && contCar < 13)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-11] = car;
        }
        else if(contCar == 13)
        {
            if(car != ':') flagErr = 1;
            else
            {
                temp[2] = '\0';
                automation->startMinute = atoi(temp);
                if(automation->startMinute < 0 || automation->startMinute > 59) flagErr = 1;
            }
        }
        else if(contCar >= 14 && contCar < 16)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-14] = car;
        }
        else if(contCar == 16)
        {
            if(car != ';') flagErr = 1;
            else
            {
                temp[2] = '\0';
                automation->startSec = atoi(temp);
                if(automation->startSec < 0 || automation->startSec > 59) flagErr = 1;
            }
        }
        else if(contCar >= 17 && contCar < 24)
        {
            if(car < '0' || car > '1') flagErr = 1;
            else automation->startDays[contCar-17] = car - '0';
        }
        else if(contCar == 24 && car != AUTO_FIELDS_SEPARATOR) flagErr = 1;
        else if(contCar >= 25 && contCar < 27)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-25] = car;
        }
        else if(contCar == 27)
        {
            if(car != ':') flagErr = 1;
            else
            {
                temp[2] = '\0';
                automation->startHour = atoi(temp);
                if(automation->startHour < 0 || automation->startHour > 23) flagErr = 1;
            }
        }
        else if(contCar >= 28 && contCar < 30)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-28] = car;
        }
        else if(contCar == 30)
        {
            if(car != ':') flagErr = 1;
            else
            {
                temp[2] = '\0';
                automation->startMinute = atoi(temp);
                if(automation->startMinute < 0 || automation->startMinute > 59) flagErr = 1;
            }
        }
        else if(contCar >= 31 && contCar < 33)
        {
            if(car < '0' || car > '9') flagErr = 1;
            else temp[contCar-31] = car;
        }
        else if(contCar == 33)
        {
            if(car == '\n' || car == '\r')
            {
                temp[2] = '\0';
                automation->startSec = atoi(temp);
                strncpy(automation->type,fileName,4);

                if(strcmp(automation->type,"DISP") != 0 && strcmp(automation->type,"GRUP") != 0) flagErr = 1;
                if(!flagErr) strcpy(automation->name,fileName+4);

                flagErr = flagErr || automation->startSec < 0 || automation->startSec > 59;

                contCar = -1;

                if(!flagErr) automations[result++] = automation;
                automation = (Automation*)(malloc(sizeof(Automation)));
            }
            else flagErr = 1;
        }

        contCar++;
    }

    close(fileIn);

    return (flagErr)? -1:result;
}

int storeAutomations(char *dirName,Automation** automations,int len)
{
    int flagErr = 0;
    int result = 0;

    DIR *dirOut = opendir(dirName);
    if(dirOut == NULL) flagErr = (mkdir(dirName,S_IRWXU) < 0);

    dirOut = opendir(dirName);
    if(dirOut == NULL) flagErr = 1;

    flagErr = flagErr || (chdir(dirName) < 0);

    struct dirent *fileDir;
    while(!flagErr && (fileDir = readdir(dirOut)) != NULL)
    {
        unlink(fileDir->d_name);
    }

    for(int cont = 0; !flagErr && cont < len; cont++)
    {
        char fileName[512];
        sprintf(fileName,"%s%s",automations[cont]->type,automations[cont]->name);
        int fileOut = open(fileName,O_WRONLY | O_CREAT);
        flagErr = flagErr || (fileOut < 0);

        char outLine[34];
        sprintf(outLine,"%d%d%d%d%d%d%d;%2d:%2d:%2d;%d%d%d%d%d%d%d;%2d:%2d:%2d\n",
                        automations[cont]->startDays[0],
                        automations[cont]->startDays[1],
                        automations[cont]->startDays[2],
                        automations[cont]->startDays[3],
                        automations[cont]->startDays[4],
                        automations[cont]->startDays[5],
                        automations[cont]->startDays[6],
                        automations[cont]->startHour,
                        automations[cont]->startMinute,
                        automations[cont]->startSec,
                        automations[cont]->stopDays[0],
                        automations[cont]->stopDays[1],
                        automations[cont]->stopDays[2],
                        automations[cont]->stopDays[3],
                        automations[cont]->stopDays[4],
                        automations[cont]->stopDays[5],
                        automations[cont]->stopDays[6],
                        automations[cont]->stopHour,
                        automations[cont]->stopMinute,
                        automations[cont]->stopSec);
        
        flagErr = flagErr || (write(fileOut,outLine,strlen(outLine)*sizeof(char)) < 0);

        close(fileOut);
    }

    flagErr = flagErr || chdir("..");
    closedir(dirOut);

    return (flagErr)? -1:result;
}