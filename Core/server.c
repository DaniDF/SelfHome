#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <string.h>
#include <signal.h>
#include <arpa/inet.h>
#include <sys/select.h>
#include <errno.h>
#include "configDevices.h"
#include "hardwareDefines.h"
#include "io.h"

#ifndef STDOUT
	#define STDOUT 1
#endif

#define MAX(a,b) (a > b)? a : b

void softStop(int numSig);
void shut(int numSig);

//int changeRequestReply(char *buffer, Device *devices[], int countDevices);
long changeRequestReply(char *buffer, Device *devices[], int countDevices);
int list(char *buffer, int len, Device *devices[], int countDevices);

int toShut[MAX_TO_SHUT];
int contToShut = 0;

char flagSleep = 0;

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

	write(STDOUT,"Avvio SelfHome.core\n",21*sizeof(char));
	write(STDOUT,"Carico dispositivi\t",19*sizeof(char));

	Device *devices[20];
	int countDevices = loadDevices(argv[2],devices,20);
	if(countDevices < 0) perror("Errore caricamento dispositivi"), exit(-2);
	write(STDOUT,"Fatto\n",6*sizeof(char));

	write(STDOUT,"Inizializzazione periferica\t",28*sizeof(char));

	if(argc == 4 && IO_init_usb(argv[3]) < 0)
	{
		perror("Errore inizializzazione periferica esterna\n\tprovo con GPIO!");
		if(IO_init() < 0) perror("Errore inizializzazione anche con GPIO"), exit(-4);
	}
	else if(argc == 3 && IO_init() < 0) perror("Errore inizializzazione GPIO"), exit(-4);

	write(STDOUT,"Fatto\n",6*sizeof(char));

	for(int count = 0; count < countDevices; count++)
	{
		if(count == 0) write(STDOUT,"Inizializzazione un dispositivo\t",33*sizeof(char));
		else printf("\rInizializzazione %d dispositivi\t",count+1);

		if(IO_write(devices[count]->pin,devices[count]->defaultStatus) < 0) perror("Errore inizializzazione dispositivo");
		else devices[count]->status = devices[count]->defaultStatus;
		if(!devices[count]->pulse) devices[count]->status = 0;
	}

	printf("Fatto\n");

	struct sockaddr_in addrServer;
	memset((char*)&addrServer,0,sizeof(addrServer));
	addrServer.sin_family = AF_INET;
	struct hostent *host;
	host = gethostbyname("localhost");
	addrServer.sin_addr.s_addr = /*((struct in_addr*)(host->h_addr))->s_addr;*/INADDR_ANY;
	addrServer.sin_port = htons(port);

	struct sockaddr_in addrClient;
	memset((char*)&addrClient,0,sizeof(addrClient));
	int lenClient = sizeof(addrClient);


	//UDP
	int sockUDP;
	if((sockUDP = socket(AF_INET,SOCK_DGRAM,0)) < 0) perror("Errore creazione socket UDP"), exit(-5);
	int on = 1;
	if(setsockopt(sockUDP, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) perror("Errore opzioni UDP"), exit(-5);
	if(bind(sockUDP,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore bind UDP"), exit(-6);
	//Fine UDP
	
	//TCP
	int sockTCP;
	if((sockTCP = socket(AF_INET,SOCK_STREAM,0)) < 0) perror("Errore creazione socket TCP"), exit(-5);
	if(setsockopt(sockTCP, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) perror("Errore opzioni TCP"), exit(-5);
	if(bind(sockTCP,(struct sockaddr*)&addrServer,sizeof(addrServer)) < 0) perror("Errore bind TCP"), exit(-6);
	if(listen(sockTCP,5) < 0) perror("Errore listen TCP"), exit(-7);
	//Fine TCP

	fd_set setRead;

	write(STDOUT,"Server online\n\n",15*sizeof(char));

	IO_sleep(), flagSleep = 1;

	struct timeval time;
	time.tv_sec = 3;
	time.tv_usec = 0;

	while(1)
	{
		struct timeval time;
		time.tv_sec = 3;
		time.tv_usec = 0;

		FD_ZERO(&setRead);
		FD_SET(sockUDP,&setRead);
		FD_SET(sockTCP,&setRead);

		if(select(MAX(sockUDP,sockTCP)+1,&setRead,NULL,NULL,&time) < 0)
		{
			if(errno == EINTR) continue;
			else perror("Errore select"), exit(-7);
		}

		char buffer[255];
		
		IO_wakeUp(), flagSleep = 0;


		if(FD_ISSET(sockUDP,&setRead))//UDP
		{
			int dim = 0;
			if((dim = recvfrom(sockUDP,buffer,255*sizeof(char),0,(struct sockaddr*)&addrClient,&lenClient)) < 0) perror("Errore receive UDP");
			else
			{
				buffer[dim-1] = '\0';
				printf("Ricevuto da %s valore %s\n",inet_ntoa(addrClient.sin_addr),buffer);

				int result = changeRequestReply(buffer,devices,countDevices);
				char sResult[4];
				if(result < 0) sprintf(sResult,"ERR");
				else if(result == 0) sprintf(sResult,"OK");
				else sprintf(sResult,"%d",result - 1);

				printf("Rispondo a %s con messaggio %s\n\n",inet_ntoa(addrClient.sin_addr),sResult);
				if(sendto(sockUDP,sResult,(strlen(sResult)+1)*sizeof(char),0,(struct sockaddr*)&addrClient,lenClient) < 0) perror("Errore scrittura");
			}
		}//Fine ISSET UDP

		if(FD_ISSET(sockTCP,&setRead))//TCP
		{
			int conn = accept(sockTCP,(struct sockaddr*)&addrClient,&lenClient);
			int dim = 0;
			int flagErr = (conn < 0) || ((dim = read(conn,buffer,255*sizeof(char))) < 0);

			if(!flagErr) buffer[dim - 1] = '\0';

			if(!flagErr && dim >= 3 && strncmp("LST",buffer,3) == 0)
			{
				printf("Ricevuto da %s valore %s\n",inet_ntoa(addrClient.sin_addr),buffer);
				char listS[512];
				flagErr = (list(listS,512,devices,countDevices) < 0);
				if(!flagErr && (flagErr = write(conn,listS,(strlen(listS)+1)*sizeof(char))) < 0) perror("Errore write TCP");
			}

			shutdown(conn,SHUT_WR);
			close(conn);
		}//Fine TCP

		if(time.tv_sec == 0 && time.tv_usec == 0) IO_sleep(), flagSleep = 1;
	}

	perror("Errore server generico");

	return -10;
}

void softStop(int numSig)
{
	printf("\rTerminazione pulita\n");

	//if(flagSleep) IO_wakeUp();
	IO_close();
	exit(0);
}

void shut(int numSig)
{
	if(flagSleep) IO_wakeUp();

	for(int cont = 0; cont < contToShut; cont++)
	{
		IO_write(toShut[cont],LOW);
	}
	
	contToShut = 0;

	if(flagSleep) IO_sleep();
}

long changeRequestReply(char *buffer, Device *devices[], int countDevices)
{
	//PROTOCOLLO: SET/GET; nomeDispositivo; statoDaSettare (SENZA SPAZI)
	int result = 0;	//Caso GET: se !flagErr allora result == 1 => OFF; result == 2 => ON
						//Caso SET: non settato;
	int bufferLen = strlen(buffer);
	int flagErr = bufferLen < 5;

	char tipoOp[4]; tipoOp[3] = '\0';
	int op = -1;
	if(!flagErr) strncpy(tipoOp,buffer,3);

	if(!flagErr && strcmp(tipoOp,"GET") == 0) op = 1;
	else if(!flagErr && strcmp(tipoOp,"SET") == 0) op = 2;
	else flagErr = 1;

	char name[MAX_DEVICE_NAME_LENGTH];
	int contName = 0;
	int offset = 4;

	while(!flagErr && offset < bufferLen && buffer[offset] != FIELDS_SEPARATOR) name[contName++] = buffer[offset++];
	name[contName] = '\0';

	if(!flagErr && op == 1)
	{
		//TODO implementare alla richiesta stato dispositivo lettura fisica dello stato salvataggio in devices[X]->status e invio al client
		flagErr = flagErr || offset != bufferLen;

		int flagFind = 0;

		if(!flagErr)	//Per dispositivo
		{
			for(int cont = 0; !flagErr && !flagFind && cont < countDevices; cont++)
			{
				if(strcmp(name,devices[cont]->name) == 0)
				{
					if(!flagErr && !devices[cont]->pulse)
					{
						if(!devices[cont]->changeable)
						{
							flagErr = flagErr || IO_read(devices[cont]->pin,&(devices[cont]->status)) < 0;
						}
						if(!flagErr) result = devices[cont]->status + 1;	//devices[cont]->status (range 0 oppure 1) result (range 1 oppure 2) per questo il '+1'
					}
					else if(!flagErr && devices[cont]->pulse) result = 1;
					
					flagFind = 1;
				}
			}
		}

		flagErr = flagErr || !flagFind;
	}
	else if(!flagErr && op == 2)
	{
		flagErr = flagErr || offset >= bufferLen;
		flagErr = flagErr || buffer[offset] != FIELDS_SEPARATOR;
		flagErr = flagErr || ++offset > bufferLen;

		int count = 0;
		while(!flagErr && buffer[offset + count] != '\0')
		{
			char car = buffer[offset + count];
			flagErr = flagErr || (!((car >= '0' && car <= '9')/* || (car >= 'A' && car <= 'F')*/));
			count++;
		}

		long stato = strtol(buffer + offset,NULL,10);
		flagErr = flagErr || (offset + count) != bufferLen;

		int flagFind = 0;

		if(!flagErr)	//Per dispositovo
		{
			for(int cont = 0; !flagFind && cont < countDevices; cont++)
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
						flagErr = flagErr || (IO_write(devices[cont]->pin,stato) < 0);
						if(!flagErr) devices[cont]->status = stato;
					}
					
				}
			}

			flagErr = flagErr || !flagFind;
		}

		if(!flagErr && flagFind) alarm(1);
		flagErr = flagErr || !flagFind;
	}

	return (flagErr)? -1 * flagErr : result;
}

int list(char *buffer, int len, Device *devices[], int countDevices)
{
	int dim = 0;

	if(len > 0) buffer[0] = '\0';

	for(int count = 0, nameLen; dim < len && count < countDevices && (nameLen = strlen(devices[count]->name)) + 1 + dim < len; count++)
	{
		strcat(buffer,devices[count]->name);
		strcat(buffer,"\n");
		dim += nameLen;
	}

	return dim;
}