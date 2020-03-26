#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <time.h>
#include <signal.h>
#include "configDevices.h"
#include "configAutomation.h"
#include "io.h"

int serverAutomation(int *channel, Device **devices, int lenDevices, Automation **automations, int lenAutomations, int *toShut, int *contToShut)
{
    int contDelated = 0;

    for(int contAutomation = 0; contAutomation < lenAutomations; contAutomation++)
    {
        int flagFind = 0;
        for(int contDevice = 0; !flagFind && contDevice < lenDevices; contDevice++)
        {
            if(strcmp(automations[contAutomation]->type,"DISP") == 0)
            {
                if(strcmp(devices[contDevice]->name,automations[contAutomation]->name) == 0) flagFind = 1;
            }
            else if(strcmp(automations[contAutomation]->type,"GRUP") == 0)
            {
                for(int contGroup = 0; !flagFind && contGroup < devices[contDevice]->contGroup; contGroup++)
                {
                    if(strcmp(devices[contDevice]->groups[contGroup],automations[contAutomation]->name) == 0) flagFind = 1;
                }
            }
        }

        if(flagFind) automations[contAutomation-contDelated] = automations[contAutomation];
        else contDelated++;
    }

    lenAutomations -= contDelated;

    if(contDelated > 0)
    {
        perror("Errore automazioni non valide");
        printf("Trovat%c %d automazion%c non valid%c!\n",(contDelated < 10)? 'a':'e',
                                                        contDelated,(contDelated < 10)? 'e':'i',
                                                        (contDelated < 10)? 'a':'e');
    }

    int contAutomation = 0;

    while(1)
    {
        time_t rowtime;
        time(&rowtime);
        struct tm *time = localtime(&rowtime);

        int date = time->tm_hour * 10000 + time->tm_min * 100 + time->tm_sec;

        int afterStart = 0;
        afterStart = automations[contAutomation]->startDays[((time->tm_wday)+6)%7]; //time->tm_wday (0 = sunday <-> 6 = saturday) io voglio (0 = monday <-> 6 = sunday)
        int startDate = automations[contAutomation]->startHour * 10000;
        startDate += automations[contAutomation]->startMinute * 100;
        startDate += automations[contAutomation]->startSec;

        afterStart = afterStart && startDate <= date;

        int beforeStop = 0;
        beforeStop = automations[contAutomation]->stopDays[((time->tm_wday)+6)%7];
        int stopDate = automations[contAutomation]->stopHour * 10000;
        stopDate += automations[contAutomation]->stopMinute * 100;
        stopDate += automations[contAutomation]->stopSec;

        beforeStop = beforeStop && stopDate > date;

        int validity = (afterStart == 1) && (beforeStop == 1);

        int flagFind = 0;
        for(int contDevice = 0; validity && !flagFind && contDevice < lenDevices; contDevice++)
        {
            if(strcmp(automations[contAutomation]->type,"DISP") == 0)
            {
                if(strcmp(devices[contDevice]->name,automations[contAutomation]->name) == 0 && devices[contDevice]->status != automations[contAutomation]->value)
                {
                    flagFind = 1;
                    printf("Automazione dispositivo %s valore %d\n\n",devices[contDevice]->name,automations[contAutomation]->value,devices[contDevice]->status);

                    if((*contToShut)+1 > MAX_TO_SHUT) {usleep(MIN_PULSE_DURATION); kill(getpid(),SIGALRM); }

                    if(devices[contDevice]->pulse)
					{
						IO_write(devices[contDevice]->pin,HIGH);
						toShut[(*contToShut)++] = devices[contDevice]->pin;
                        devices[contDevice]->status = automations[contAutomation]->value;
                        write(channel[1],&contDevice,sizeof(int));
                        write(channel[1],&(devices[contDevice]->status),sizeof(int));
					}
					else
					{
						IO_write(devices[contDevice]->pin,automations[contAutomation]->value);
						devices[contDevice]->status = automations[contAutomation]->value;
                        write(channel[1],&contDevice,sizeof(int));
                        write(channel[1],&(devices[contDevice]->status),sizeof(int));
					}
                }
            }
            else if(strcmp(automations[contAutomation]->type,"GRUP") == 0)
            {
                for(int contGroup = 0; !flagFind && contGroup < devices[contDevice]->contGroup; contGroup++)
                {
                    if(strcmp(devices[contDevice]->groups[contGroup],automations[contAutomation]->name) == 0 && devices[contDevice]->status != automations[contAutomation]->value)
                    {
                        flagFind = 1;
                        printf("Automazione dispositivo %s valore %d\n\n",devices[contDevice]->name,automations[contAutomation]->value);
                        
                        if((*contToShut)+1 > MAX_TO_SHUT) {usleep(MIN_PULSE_DURATION); kill(getpid(),SIGALRM); }

                        if(devices[contDevice]->pulse)
                        {
                            IO_write(devices[contDevice]->pin,HIGH);
                            toShut[(*contToShut)++] = devices[contDevice]->pin;
                            devices[contDevice]->status = automations[contAutomation]->value;
                            write(channel[1],&contDevice,sizeof(int));
                            write(channel[1],&(devices[contDevice]->status),sizeof(int));
                        }
                        else
                        {
                            IO_write(devices[contDevice]->pin,automations[contAutomation]->value);
                            devices[contDevice]->status = automations[contAutomation]->value;
                            write(channel[1],&contDevice,sizeof(int));
                            write(channel[1],&(devices[contDevice]->status),sizeof(int));
                        }
                    }
                }
            }

            if(flagFind) alarm(1);
        }

        contAutomation++;
        if(contAutomation == lenAutomations)
        {
            contAutomation = 0;
            sleep(1);
        }
    }

    exit(0);
}