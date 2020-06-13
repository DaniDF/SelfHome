#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <time.h>
#include <signal.h>
#include "configDevices.h"
#include "configAutomation.h"

int serverAutomation(int port, char* dirAutomationsName)
{
    Automation *automations[50];
    int lenAutomations;
    if((lenAutomations = loadAutomations(dirAutomationsName,automations,50)) < 0) perror("Errore impossibile caricare le automazioni"), exit(-1);

    struct sockaddr_in addrClient;
    memset((char*)&addrClient,0,sizeof(addrClient));
    addrClient.sin_family = AF_INET;
    addrClient.sin_addr.s_addr = INADDR_ANY;
    addrClient.sin_port = 0;

    struct sockaddr_in addrServer;
    memset((char*)&addrServer,0,sizeof(addrServer));
    addrServer.sin_family = AF_INET;
    struct hostent *host = gethostbyname("localhost");
	if(host == NULL) perror("Errore impossibile contattare il server"), exit(-1);
    addrServer.sin_addr.s_addr = ((struct in_addr*)(host->h_addr))->s_addr;
    addrServer.sin_port = htons(port);

    struct sockaddr_in temp;
	memset((char*)&temp,0,sizeof(temp));
	int lenTemp = sizeof(temp);

    int sockUDP;
	if((sockUDP = socket(AF_INET,SOCK_DGRAM,0)) < 0) perror("Errore creazione socket UDP"), exit(-2);
	if(bind(sockUDP,(struct sockaddr*)&addrClient,sizeof(addrClient)) < 0) perror("Errore bind UDP"), exit(-3);

    int contAutomation = 0;

    while(lenAutomations > 0)
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

        if(validity)
        {
            char cmd[MAX_NAME_LENGTH + 13];
            sprintf(cmd,"SET;%s;%s;%d",automations[contAutomation]->type,automations[contAutomation]->name,automations[contAutomation]->value);

            if(sendto(sockUDP,cmd,(strlen(cmd)+1)*sizeof(char),0,(struct sockaddr*)&addrServer,sizeof(addrServer)) >= 0)
            {
                if(recvfrom(sockUDP,cmd,5*sizeof(char),0,(struct sockaddr*)&temp,&lenTemp) < 0) perror("Errore ricezione comando");
            }
            else perror("Errore invio automazione");
        }
        
        if(++contAutomation >= lenAutomations) contAutomation = 0, sleep(1);
    }

    perror("Terminazione gestore automazioni");

    close(sockUDP);

    pause();

    exit(0);
}