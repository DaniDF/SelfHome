#include "hardwareDefines.h"
#include "rs232.h"

static int IO_dev = -1;

#ifndef IO_WELCOME_MESSAGE
	#define IO_WELCOME_MESSAGE 0x7E
#endif

#ifndef IO_GOODBYE_MESSAGE
	#define IO_GOODBYE_MESSAGE 0x6F
#endif

#ifndef IO_OK_RESPONSE
	#define IO_OK_RESPONSE 0x20
#endif

#ifndef IO_ERR_RESPONSE
	#define IO_ERR_RESPONSE 0x4F
#endif

#ifndef IO_NOT_ASSIGNED
    #define IO_NOT_ASSIGNED -1
#endif

#ifndef IO_BCM2835_SEL
    #define IO_BCM2835_SEL -10
#endif

#ifndef HIGH
    #define HIGH 1
#endif

#ifndef LOW
    #define LOW 0
#endif

#ifndef BDRATE
	#define BDRATE 115200
#endif

#ifndef MODE
	#define MODE "8N1"
#endif

#ifndef FLOWCTRL
	#define FLOWCTRL 0
#endif

//Inizializzazione
int IO_init(void);
int IO_init_usb(char *devName);

//Scrittura
int IO_write(int pin, int value);

//Lettura
int IO_read(int pin, short int *value);

//Sospensione risorse
int IO_sleep(void);

//Riacquisizione risorse
int IO_wakeUp(void);

//Chiusura
int IO_close(void);