#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <string.h>
#include <errno.h>
#include <arpa/inet.h>

#ifndef STDIN
	#define STDIN 0
#endif

#ifndef STDOUT
	#define STDOUT 1
#endif

#ifndef MULTICAST_ADDR
	#define MULTICAST_ADDR "224.24.46.24"
#endif

int main(int argc, char *argv[])
{
	if(argc > 4) perror("Errore numero argomenti"), exit(-1);

	struct hostent *host;
	int port;

	struct sockaddr_in addrClient;
	memset((char*)&addrClient,0,sizeof(addrClient));
	addrClient.sin_family = AF_INET;
	addrClient.sin_addr.s_addr = INADDR_ANY;
	addrClient.sin_port = 0;

	struct sockaddr_in temp;
	memset((char*)&temp,0,sizeof(temp));
	int lenTemp = sizeof(temp);

	int sockUDP;
	if((sockUDP = socket(AF_INET,SOCK_DGRAM,0)) < 0) perror("Errore creazione socket UDP"), exit(-2);
	if(bind(sockUDP,(struct sockaddr*)&addrClient,sizeof(addrClient)) < 0) perror("Errore bind UDP"), exit(-3);

	int flagList = ((argc == 2 || argc == 4) && strcmp(argv[1],"-l") == 0);

	for(int cont = 1; flagList && cont < argc-1; cont++)
	{
		argv[cont] = argv[cont+1];
	}

	if(flagList) argc--;

	if(argc == 1)
	{
		struct sockaddr_in addrServerSSDP;
		memset((char*)&addrServerSSDP,0,sizeof(addrServerSSDP));
		addrServerSSDP.sin_family = AF_INET;
		struct hostent *group = gethostbyname(MULTICAST_ADDR);
		if(group == NULL) perror("Errore indirizzo di multicast"), exit(-1);
		addrServerSSDP.sin_addr.s_addr = ((struct in_addr*)(group->h_addr))->s_addr;
		addrServerSSDP.sin_port = htons(4000);

		struct ip_mreq mreq;
		mreq.imr_multiaddr.s_addr = inet_addr(MULTICAST_ADDR);
		mreq.imr_interface.s_addr = INADDR_ANY;
		if(setsockopt(sockUDP,IPPROTO_IP,IP_ADD_MEMBERSHIP,&mreq,sizeof(&mreq)) < 0) perror("Errore opzioni SSDP"), exit(-3);

		if(sendto(sockUDP,"WHEREAREYOU",12*sizeof(char),0,(struct sockaddr*)&addrServerSSDP,sizeof(addrServerSSDP)) < 0) perror("Errore ssdp send"), exit(-11);
		char response[255];
		if(recvfrom(sockUDP,response,255*sizeof(char),0,(struct sockaddr*)&temp,&lenTemp) < 0) perror("Errore recv ssdp"), exit(-11);
		printf("Ricevuto: %s\n",response);

		char ip[16];
		char firstPort[4];
		char lastPort[4];
		int flagDone = 0;

		for(int cont = 0, contSep = 0, offset = 0; cont < strlen(response) + 1; cont++)
		{
			if(response[cont] == ',' || response[cont] == '\0') contSep++,flagDone = 0;

			if(contSep < 4)
			{
				if(response[cont] == ',') ip[cont] = '.';
				else ip[cont] = response[cont];
			}
			else if(contSep == 4 && !flagDone) ip[cont] = '\0', offset = cont+1, flagDone = 1;
			else if(contSep < 5)
			{
				if(response[cont] < '0' || response[cont] > '9') perror("Errore risposta server - porta non valida"), exit(-1);
				else firstPort[cont-offset] = response[cont];
			}
			else if(contSep == 5 && !flagDone) firstPort[cont-offset] = '\0', offset = cont+1, flagDone = 1;
			else if(contSep < 6)
			{
				if(response[cont] < '0' || response[cont] > '9') perror("Errore risposta server - porta non valida"), exit(-1);
				else lastPort[cont-offset] = response[cont];
			}
			else if(contSep == 6 && !flagDone) lastPort[cont-offset] = '\0', flagDone = 1;
			else perror("Errore risposta server - formato non valido"), exit(-1);
		}

		host = gethostbyname(ip);
		if(host == NULL) perror("Errore risposta server - ip non valido");
		port = atoi(firstPort)*256 + atoi(lastPort);
	}
	else
	{
		for(int cont = 0; cont < strlen(argv[2]); cont++)
		{
			if(argv[2][cont] < '0' || argv[2][cont] > '9') perror("Errore porta non numerica"), exit(-1);
		}
		host = gethostbyname(argv[1]);
		if(host == NULL) perror("Errore ip non valido"), exit(-1);
		port = atoi(argv[2]);
		if(port < 1024) perror("Porta non valida"), exit(-1);
	}

	struct sockaddr_in addrServer;
	memset((char*)&addrServer,0,sizeof(addrServer));
	addrServer.sin_family = AF_INET;	//Campo ip compilato piu' avanti
	addrServer.sin_addr.s_addr = ((struct in_addr*)(host->h_addr))->s_addr;
	addrServer.sin_port = htons(port);

	if(flagList)
	{
		int sockTCP;
		if((sockTCP = socket(AF_INET,SOCK_STREAM,0)) < 0) perror("Errore socket TCP"), exit(-2);
		if(connect(sockTCP,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore connect"), exit(-3);

		if(write(sockTCP,"LST",4*sizeof(char)) < 0) perror("Errore invio get"), exit(-4);

		char car;
		while(read(sockTCP,&car,sizeof(char)) > 0)
			write(STDOUT,(car == '\0')? "\n":&car,sizeof(char));

		close(sockTCP);
	}

	char line[255];
	int contLine = 0;
	while(!flagList && contLine < 255 && read(STDIN,&(line[contLine]),sizeof(char)) > 0)
	{
		if(line[contLine] == '\n')
		{
			//line[contLine] = '\0';
			if(sendto(sockUDP,line,(contLine+1)*sizeof(char),0,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore scrittura");

			if(recvfrom(sockUDP,line,255*sizeof(char),0,(struct sockaddr*)&temp,&lenTemp) < 0) perror("Errore receive");
			else write(STDOUT,line,strlen(line)*sizeof(char)), write(STDOUT,"\n",sizeof(char));

			contLine = 0;
		}
		else contLine++;
	}

	close(sockUDP);

	return 0;
}
