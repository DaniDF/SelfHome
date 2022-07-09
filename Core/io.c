#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "io.h"

#define WAIT_TIME 2000

int max = 0;
char *devNameStore = NULL;

//Inizializzazione
int IO_init(void)   //Da GPIO
{
    int result = -1;

    if(IO_dev == IO_NOT_ASSIGNED)  //Se inizializzazione ancora da fare
    {
        #if !DEBUG
        result = ((result = bcm2835_init()) == 0)? -1:result;
        #endif

        if(result >= 0) IO_dev = IO_BCM2835_SEL, max = 27;  //Massimo pin gpio = 27
    }

    return result;
}

int IO_init_usb(char *devName)  //Da file (e.g. usb)
{
    int flagErr = 0;
    devNameStore = devName;

    if(IO_dev == IO_NOT_ASSIGNED)  //Se inizializzazione non ancora fatta
    {
        int portNum = RS232_GetPortnr(devName);
        if(portNum < 0) flagErr = 1;
        else if(RS232_OpenComport(portNum, BDRATE, MODE,FLOWCTRL) != 0) flagErr = 1;
        
        IO_dev = (flagErr)? IO_NOT_ASSIGNED : portNum;
        if(flagErr) perror("IO: Porta non assegnata");

        char response;
        flagErr = flagErr || (RS232_SendByte(IO_dev,IO_WELCOME_MESSAGE) != 1 /*!= 0*/);
        if(flagErr) perror("IO: Errore invio welcome");
        usleep(WAIT_TIME);
        flagErr = flagErr || (RS232_PollComport(IO_dev,&response,sizeof(char)) != sizeof(char));
        if(flagErr) perror("IO: Errore ricezione risposta"), printf("ricevuto = %d\n",(int)response);
        flagErr = flagErr || response != IO_WELCOME_MESSAGE;
        if(flagErr) perror("IO: Errore ricevuto non welcome"), printf("ricevuto = %d\n",(int)response);
    }
    else flagErr = 1;   //Se inizializzazione fatta

    return -1 * flagErr;
}

//Scrittura
int IO_write(int pin, long value)
{
    int flagErr = 0;//(pin > max);

    if(!flagErr && IO_dev == IO_BCM2835_SEL)    //GPIO
    {
        #if !DEBUG
        bcm2835_gpio_fsel(pin,BCM2835_GPIO_FSEL_OUTP);
		bcm2835_gpio_write(pin,!value);
        #endif
    }
    else if(!flagErr && IO_dev > 0) //usb
    {
        char buffer[128];
        sprintf(buffer,"SET;%d;%li\n",pin,value);
        flagErr = (RS232_SendBuf(IO_dev,buffer,strlen(buffer)*sizeof(char)) < 0);
        if(flagErr) perror("IO: WRITE: Errore invio");
        else usleep(WAIT_TIME);

        flagErr = (RS232_PollComport(IO_dev,buffer,sizeof(char)) != 1);
        if(flagErr) perror("IO: WRITE: Errore ricezione"), printf("ricevuto = %d\n",(int)(buffer[0]));
        flagErr = flagErr || buffer[0] == IO_ERR_RESPONSE || buffer[0] != IO_OK_RESPONSE;

        if(flagErr && buffer[0] == IO_ERR_RESPONSE) perror("IO: WRITE: Errore ricevuto messaggio errore");
        else if(flagErr && buffer[0] != IO_OK_RESPONSE) perror("IO: WRITE: Errore ricevuto non OK");
    }

    return -1 * flagErr;
}

//Lettura
int IO_read(int pin, long *value)
{
    int flagErr = 0;//(pin > max);

    if(!flagErr && IO_dev == IO_BCM2835_SEL)    //GPIO
    {
        #if !DEBUG
        bcm2835_gpio_fsel(pin,BCM2835_GPIO_FSEL_OUTP);
		*value = bcm2835_gpio_lev(pin);
        #endif
    }
    else if(!flagErr && IO_dev > 0) //usb
    {
        char buffer[16];
        sprintf(buffer,"GET;%d\n",pin);
        flagErr = (RS232_SendBuf(IO_dev,buffer,strlen(buffer)*sizeof(char)) < 0);
        if(flagErr) perror("IO: READ: Errore invio");
        else usleep(WAIT_TIME * (((pin / 100) > 0)? 20:1));

        flagErr = (RS232_PollComport(IO_dev,buffer,sizeof(char)) != 1);
        if(flagErr) perror("IO: READ: Errore ricezione");
        flagErr = flagErr || (buffer[0] < 0);
        if(!flagErr) *value = buffer[0];

        if(flagErr && (buffer[0] < 0)) perror("IO: READ: Errore ricevuto messaggio non valido");
    }

    return -1 * flagErr;
}

//Sospensione risorse
int IO_sleep(void)
{
    if(IO_dev >= 0)
    {
        RS232_CloseComport(IO_dev);  //File
        close(IO_dev);
    }

    IO_dev = IO_NOT_ASSIGNED;

    return 0;
}

//Riacquisizione risorse
int IO_wakeUp(void)
{
    int flagErr = 0;

    if(IO_dev == IO_NOT_ASSIGNED && devNameStore != NULL)
    {
        flagErr = IO_init_usb(devNameStore);
        if(flagErr) perror("IO: wakeup: Errore init");
    }

    return flagErr;
}

//Chiusura
int IO_close(void)
{
    if(IO_dev >= 0)
    {
        RS232_SendByte(IO_dev,IO_GOODBYE_MESSAGE);
        RS232_CloseComport(IO_dev);  //File
        close(IO_dev);
    }

    IO_dev = IO_NOT_ASSIGNED;

    return 0;
}