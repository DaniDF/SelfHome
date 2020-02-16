#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <string.h>
#include <signal.h>
#include <arpa/inet.h>
#include <ifaddrs.h> //per mreq
#include <sys/select.h>
#include <errno.h>
#include "configDevices.h"
#include "hardwareDefines.h"
#include "io.h"

#ifndef STDOUT
	#define STDOUT 1
#endif

#ifndef MULTICAST_ADDR
	#define MULTICAST_ADDR "224.24.46.24"
#endif

#define MAX(x,y) (x>y)? x:y

void softStop(int numSig);
void shut(int numSig);

char *wayReply(int port);
int changeRequestReply(char *buffer, Device *devices[], int contDevices);

int toShut[MAX_TO_SHUT];
int contToShut = 0;

int main(int argc, char *argv[])
{
	signal(SIGINT,softStop);
	signal(SIGALRM,shut);

	if(argc != 3 && argc != 4) perror("Errore numero argomenti"), exit(-1);

	for(int cont = 0; cont < strlen(argv[1]); cont++)
	{
		if(argv[1][cont] < '0' || argv[1][cont] > '9') perror("Errore porta non numerica"), exit(-1);
	}
	int port = atoi(argv[1]);
	if(port < 1024) perror("Porta non valida"), exit(-1);

	write(STDOUT,"Avvio server\n",13*sizeof(char));
	write(STDOUT,"Carico dispositivi\t",19*sizeof(char));

	Device *devices[MAX_DEVICE];
	int contDevices = loadDevices(argv[2],devices,20);
	if(contDevices < 0) perror("Errore caricamento dispositivi"), exit(-2);

	write(STDOUT,"Fatto\n",6*sizeof(char));
	write(STDOUT,"Inizializzazione periferica\t",28*sizeof(char));

	if(argc == 4 && IO_init_usb(argv[3]) < 0)
	{
		perror("Errore inizializzazione periferica esterna\n\tprovo con GPIO!");
		if(IO_init() < 0) perror("Errore inizializzazione anche con GPIO"), exit(-2);
	}
	else if(argc == 3 && IO_init() < 0) perror("Errore inizializzazione GPIO"), exit(-2);

	write(STDOUT,"Fatto\n",6*sizeof(char));

	for(int cont = 0; cont < contDevices; cont++)
	{
		if(cont == 0) write(STDOUT,"Inizializzazione un dispositivo\t",30*sizeof(char));
		else printf("\rInizializzazione %d dispositivi\t",cont+1);

		IO_write(devices[cont]->pin,LOW);
	}

	printf("Fatto\n");

	struct sockaddr_in addrServer;
	memset((char*)&addrServer,0,sizeof(addrServer));
	addrServer.sin_family = AF_INET;
	addrServer.sin_addr.s_addr = INADDR_ANY;
	addrServer.sin_port = htons(port);

	struct sockaddr_in addrClient;
	memset((char*)&addrClient,0,sizeof(addrClient));
	int lenClient = sizeof(addrClient);


	//UDP
	int sockUDP;
	if((sockUDP = socket(AF_INET,SOCK_DGRAM,0)) < 0) perror("Errore creazione socket UDP"), exit(-3);
	int on = 1;
	if(setsockopt(sockUDP, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) perror("Errore opzioni UDP"), exit(-3);

	if(bind(sockUDP,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore bind UDP"), exit(-4);

	struct ip_mreq mreq;
	mreq.imr_multiaddr.s_addr = inet_addr(MULTICAST_ADDR);
	mreq.imr_interface.s_addr = INADDR_ANY;
	if(setsockopt(sockUDP,IPPROTO_IP,IP_ADD_MEMBERSHIP,&mreq,sizeof(mreq)) < 0) perror("Errore opzioni SSDP"), exit(-3);
	//Fine UDP

	//TCP
	int sockTCP;
	if((sockTCP = socket(AF_INET,SOCK_STREAM,0)) < 0) perror("Errore creazione socket TCP"), exit(-3);
	if(setsockopt(sockTCP, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) perror("Errore opzioni TCP"), exit(-3);
	if(bind(sockTCP,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore bind TCP"), exit(-4);
	if(listen(sockTCP,5) < 0) perror("Errore listen"), exit(-5);
	//Fine TCP

	fd_set setRead;

	write(STDOUT,"Server online\n\n",15*sizeof(char));

	while(1)
	{		
		FD_ZERO(&setRead);
		FD_SET(sockUDP,&setRead);
		FD_SET(sockTCP,&setRead);

		if(select(MAX(sockUDP,sockTCP)+1,&setRead,NULL,NULL,NULL) < 0)
		{
			if(errno == EINTR) continue;
			else perror("Errore select"), exit(-6);
		}

		char buffer[255];
		int dim = 0;

		if(FD_ISSET(sockUDP,&setRead))//UDP
		{
			if((dim = recvfrom(sockUDP,buffer,255*sizeof(char),0,(struct sockaddr*)&addrClient,&lenClient)) < 0) perror("Errore receive UDP");
			else
			{
				buffer[dim-1] = '\0';
				printf("Ricevuto da %s valore %s\n",inet_ntoa(addrClient.sin_addr),buffer);

				if(strcmp(buffer,"WHEREAREYOU") == 0)
				{
					char *response = wayReply(port);
					int flagErr = (response == NULL)? 1:0;

					printf("Rispondo con: %s\n\n",(flagErr)? "ERR":response);
					if(!flagErr && sendto(sockUDP,response,(strlen(response) + 1)*sizeof(char),0,(struct sockaddr*)&addrClient,lenClient) < 0) perror("Errore send");
					else if(flagErr && sendto(sockUDP,"ERR",4*sizeof(char),0,(struct sockaddr*)&addrClient,lenClient) < 0) perror("Errore send");
				}
				else
				{
					int result = changeRequestReply(buffer,devices,contDevices);
					char sResult[4];
					if(result < 0) sprintf(sResult,"ERR");
					else if(result == 0) sprintf(sResult,"OK");
					else sprintf(sResult,"%d",result - 1);

					printf("Rispondo a %s con messaggio %s\n\n",inet_ntoa(addrClient.sin_addr),sResult);
					if(sendto(sockUDP,sResult,(strlen(sResult)+1)*sizeof(char),0,(struct sockaddr*)&addrClient,lenClient) < 0) perror("Errore scrittura");
				}
			}
		}//Fine ISSET UDP


		if(FD_ISSET(sockTCP,&setRead))//TCP
		{
			if(!fork())
			{
				signal(SIGINT,SIG_DFL);
				signal(SIGALRM,SIG_DFL);
				close(sockUDP);
				int conn = accept(sockTCP,(struct sockaddr*)&addrClient,&lenClient);

				int flagErr = (conn < 0);

				if((dim = read(conn,buffer,255*sizeof(char))) < 0) perror("Errore read TCP"), flagErr = 1;

				if(!flagErr)
				{
					buffer[dim-1] = '\0';
					printf("Ricevuta connessione da %s valore %s\n\n",inet_ntoa(addrClient.sin_addr),buffer);

					flagErr = flagErr || (strcmp(buffer,"GET") != 0);

					if(!flagErr)
					{
						for(int contD = 0; !flagErr && contD < contDevices; contD++)
						{
							char disp[MAX_NAME_LENGTH+7];
							sprintf(disp,"DISP;%s\n",devices[contD]->name);
							flagErr = flagErr || (write(conn,disp,(strlen(disp))*sizeof(char)) < 0);

							for(int contG = 0; !flagErr && contG < devices[contD]->contGroup; contG++)
							{
								char grup[MAX_GROUP_NAME_LENGTH+7];
								sprintf(grup,"GRUP;%s\n",devices[contD]->groups[contG]);
								flagErr = flagErr || (write(conn,grup,(strlen(grup))*sizeof(char)) < 0);
							}
						}

						flagErr = flagErr || (write(conn,"\0",sizeof(char)) < 0);
					}
				}

				shutdown(conn,SHUT_WR);
				close(conn);
				close(sockTCP);

				exit(flagErr);
			}
		}//Fine ISSET TCP
	}

	perror("Errore server generico");

	return -10;
}

void softStop(int numSig)
{
	printf("\rTerminazione pulita\n");
	IO_close();
	exit(0);
}

void shut(int numSig)
{
	for(int cont = 0; cont < contToShut; cont++)
	{
		IO_write(toShut[cont],LOW);
	}
	
	contToShut = 0;
}

//WhereAreYou
char *wayReply(int port)
{
	int flagErr = 0;
	struct ifaddrs *ifa;
	
	if(getifaddrs(&ifa) < 0) flagErr = 1;

	static char response[24];

	if(!flagErr)
	{
		struct ifaddrs *ifaTemp = ifa;

		struct sockaddr_in iface = *((struct sockaddr_in*)(ifaTemp->ifa_addr));

		while(ifaTemp != NULL && (iface.sin_addr.s_addr >> (8*3)) < 2)
		{
			ifaTemp = ifaTemp->ifa_next;
			iface = *((struct sockaddr_in*)(ifaTemp->ifa_addr));
		}
		
		flagErr = flagErr || ifaTemp == NULL;

		if(!flagErr)
		{
			sprintf(response,"%s,%d,%d",inet_ntoa(iface.sin_addr),port/256,port%256);

			for(int cont = 0; cont < strlen(response); cont++)
			{
				if(response[cont] == '.') response[cont] = ',';
			}
		}
	}

	freeifaddrs(ifa);

	return (flagErr)? NULL:response;
}

int changeRequestReply(char *buffer, Device *devices[], int contDevices)
{
	//PROTOCOLLO: SET/GET; DISP/GRUP; nomeDispositivo/numeGruppo; statoDaSettare (SENZA SPAZI)
	int result = 0;	//Caso GET: se !flagErr allora result == 1 => OFF; result == 2 => ON
						//Caso SET: non settato;
	int bufferLen = strlen(buffer);
	int flagErr = bufferLen < 13;

	char tipoOp[4]; tipoOp[3] = '\0';
	int op = -1;
	if(!flagErr) strncpy(tipoOp,buffer,3);

	if(!flagErr && strcmp(tipoOp,"GET") == 0) op = 1;
	else if(!flagErr && strcmp(tipoOp,"SET") == 0) op = 2;
	else flagErr = 1;

	char tipoDG[5]; tipoDG[4] = '\0';
	int flagDisp = -1;
	if(!flagErr) strncpy(tipoDG,buffer+3+1,4);
	if(!flagErr && strcmp(tipoDG,"DISP") == 0) flagDisp = 1;
	else if(!flagErr && strcmp(tipoDG,"GRUP") == 0) flagDisp = 0;
	else flagErr = 1;

	char name[MAX(MAX_NAME_LENGTH,MAX_GROUP_NAME_LENGTH)];
	int contName = 0;
	int offset = 9;

	while(!flagErr && offset < bufferLen && buffer[offset] != FIELDS_SEPARATOR) name[contName++] = buffer[offset++];
	name[contName] = '\0';

	if(!flagErr && op == 1)
	{
		//TODO implementare alla richiesta stato dispositivo lettura fisica dello stato salvataggio in devices[X]->status e invio al client
		flagErr = flagErr || offset != bufferLen;

		int flagFind = 0;

		if(!flagErr && flagDisp)	//Per dispositivo
		{
			for(int cont = 0; !flagErr && !flagFind && cont < contDevices; cont++)
			{
				if(strcmp(name,devices[cont]->name) == 0)
				{
					flagErr = flagErr || devices[cont]->pulse;
					if(!flagErr) result = devices[cont]->status + 1;	//devices[cont]->status (range 0 oppure 1) result (range 1 oppure 2) per questo il '+1'
					flagFind = 1;
					printf("flagErr = %d\tresult = %d\n",flagErr,result);
				}
			}
		}
		else if(!flagErr && !flagDisp)
		{
			flagErr = 1;
		}

		flagErr = flagErr || !flagFind;
	}
	else if(!flagErr && op == 2)
	{
		flagErr = flagErr || offset >= bufferLen;
		flagErr = flagErr || buffer[offset] != FIELDS_SEPARATOR;
		flagErr = flagErr || ++offset > bufferLen;

		flagErr = flagErr || (buffer[offset] != '0' && buffer[offset] != '1');
		int stato = atoi(buffer+offset);
		flagErr = flagErr || ++offset != bufferLen;

		int flagFind = 0;

		if(!flagErr && flagDisp)	//Per dispositovo
		{
			for(int cont = 0; !flagFind && cont < contDevices; cont++)
			{
				if(strcmp(name,devices[cont]->name) == 0 && devices[cont]->changeable)
				{
					flagFind = 1;

					if(contToShut+1 > MAX_TO_SHUT) {usleep(MIN_PULSE_DURATION); kill(getpid(),SIGALRM); }

					if(devices[cont]->pulse)
					{
						flagErr = flagErr || (IO_write(devices[cont]->pin,HIGH) < 0);
						toShut[contToShut++] = devices[cont]->pin;
					}
					else
					{
						flagErr = flagErr || (IO_write(devices[cont]->pin,!devices[cont]->status) < 0);
						if(!flagErr) devices[cont]->status = !devices[cont]->status;
					}
					
				}
			}

			flagErr = flagErr || !flagFind;
		}
		else if(!flagErr && !flagDisp)	//Per gruppo
		{
			for(int contD = 0; contD < contDevices; contD++)
			{
				for(int contG = 0; contG < devices[contD]->contGroup; contG++)
				{
					if(strcmp(name,devices[contD]->groups[contG]) == 0 && devices[contD]->changeable)
					{
						flagFind = 1;

						if(contToShut+1 > MAX_TO_SHUT) {usleep(MIN_PULSE_DURATION); kill(getpid(),SIGALRM); }

						if(devices[contD]->pulse)
						{
							flagErr = flagErr || (IO_write(devices[contD]->pin,HIGH) < 0);
							toShut[contToShut++] = devices[contD]->pin;
						}
						else
						{
							flagErr = flagErr || (IO_write(devices[contD]->pin,!devices[contD]->status) < 0);
							if(!flagErr) devices[contD]->status = !devices[contD]->status;
						}
					}
				}
			}
		}

		if(!flagErr && flagFind) alarm(1);
		flagErr = flagErr || !flagFind;
	}

	return (flagErr)? -1 * flagErr : result;
}
