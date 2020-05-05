#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "io.h"

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
        //if(flagErr) perror("IO: Porta non assegnata");

        char response;
        flagErr = flagErr || (RS232_SendByte(IO_dev,IO_WELCOME_MESSAGE) != 0);
        //if(flagErr) perror("IO: Errore invio welcome");
        usleep(10000);
        flagErr = flagErr || (RS232_PollComport(IO_dev,&response,sizeof(char)) != sizeof(char));
        //if(flagErr) perror("IO: Errore ricezione risposta");
        flagErr = flagErr || response != IO_WELCOME_MESSAGE;
        //if(flagErr) perror("IO: Errore ricevuto non welcome");
        flagErr = flagErr || (RS232_PollComport(IO_dev,(char*)&max,sizeof(char)) != sizeof(char));  //Leggo il massimo pin che è in grado di gestire la periferica
        //if(flagErr) perror("IO: Errore lettura max");
    }
    else flagErr = 1;   //Se inizializzazione fatta

    return -1 * flagErr;
}

//Scrittura
int IO_write(int pin, int value)
{
    int flagErr = (pin > max);

    if(!flagErr && IO_dev == IO_BCM2835_SEL)    //GPIO
    {
        #if !DEBUG
        bcm2835_gpio_fsel(pin,BCM2835_GPIO_FSEL_OUTP);
		bcm2835_gpio_write(pin,!value);
        #endif
    }
    else if(!flagErr && IO_dev > 0) //usb
    {
        char buffer[8];
        sprintf(buffer,"SET;%d;%d\n",pin,value);
        flagErr = (RS232_SendBuf(IO_dev,buffer,8*sizeof(char)) < 0);
        if(flagErr) /*perror("IO: WRITE: Errore invio")*/;
        else usleep(10000);

        flagErr = (RS232_PollComport(IO_dev,buffer,sizeof(char)) != 1);
        //if(flagErr) perror("IO: WRITE: Errore ricezione");
        flagErr = flagErr || buffer[0] == IO_ERR_RESPONSE || buffer[0] != IO_OK_RESPONSE;

        //if(flagErr && buffer[0] == IO_ERR_RESPONSE) perror("IO: WRITE: Errore ricevuto messaggio errore");
        //else if(flagErr && buffer[0] != IO_OK_RESPONSE) perror("IO: WRITE: Errore ricevuto non OK");
    }

    return -1 * flagErr;
}

//Lettura
int IO_read(int pin, int *value)
{
    return -1;
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