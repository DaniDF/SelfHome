#define DEBUG 0

#include <EEPROM.h>

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

#ifndef MAX_DISP
  #define MAX_DISP 12
#endif

#ifndef BUFFER_DIM
  #define BUFFER_DIM 32
#endif

#define APPEAL_INTERVAL 60000

#define RST '0';
#define SET '1';
#define GET '2';
#define IDY '3';

#define USB Serial
#define SER Serial1
#define LED 13

#define RESET_BUT A0
#define RESET_BUT_INTERVAL 1000

struct changeValue
{
  short operation;
  short pin;
  long value;
};

void setup() {
  pinMode(LED,OUTPUT);
  pinMode(RESET_BUT,INPUT);
  for(int cont = 2; cont < 13; cont++) { pinMode(cont,OUTPUT); digitalWrite(cont,HIGH); }
  USB.begin(115200);
  SER.begin(9600);
}

void loop() {
  static int flagAvvio = 0;
  static long oldTimeBlink = millis();
  static short stateBlink = 0;
  static long oldTimeFlash = millis();
  static short stateFlash = 0;
  static char buffer[BUFFER_DIM];
  static short contBuffer = 0;
  static long oldAppeal = -APPEAL_INTERVAL * 10;
  static byte address[8] = {0};
  static byte data[12] = {0};
  static byte periferics[128][8] = {0};
  static int countPeriferics = 0;
  static byte flagAttendData = 0;
  static long oldReadResetBut = 0;

  /*static boolean isPerifericsInit = false;
  if(!isPerifericsInit)
  {
    isPerifericsInit = true;
    countPeriferics = initPeriferics(periferics,128);
  }*/
  
  if(!flagAvvio)  //Se non ho ancora ricevuto il benvenuto
  {
    if(millis() - oldTimeBlink > 1000) { digitalWrite(LED,(stateBlink = !stateBlink)); oldTimeBlink = millis(); }
  }
  else
  {
    if(stateFlash > 0 && (millis() - oldTimeFlash) > 333) { digitalWrite(LED,--stateFlash%2); oldTimeFlash = millis(); }
  }
  
  if(USB.available())
  {
    buffer[contBuffer] = USB.read();

    if(buffer[contBuffer] == IO_WELCOME_MESSAGE)
    {
      USB.write(IO_WELCOME_MESSAGE);
      digitalWrite(LED,LOW);
      flagAvvio = 1;
    }
    else if(buffer[contBuffer] == IO_GOODBYE_MESSAGE)
    {
      flagAvvio = 0;
    }
    else if(flagAvvio && contBuffer > 0 && buffer[contBuffer] == '\n')
    {
      buffer[contBuffer] = '\0';

      struct changeValue value;
      if(parseInput(buffer,&value) == 0)
      {
        if(value.operation == 1) //GET
        {
          //TODO implementare logica lettura stato
          if(value.pin < 100)
          {
            pinMode(value.pin,INPUT);

            USB.write(!digitalRead(value.pin));
            pinMode(value.pin,OUTPUT);
          }
          else if((value.pin/100) - 1 < countPeriferics)
          {
            data[0] = GET;
            data[1] = (byte)(value.pin%100);
            xbee_send(periferics[(value.pin/100) - 1],data,2);
            flagAttendData++;
          }
        }
        else if(value.operation == 2)  //SET
        {
          if(value.pin < 100)
          {
            digitalWrite(value.pin,!value.value);
            USB.write(IO_OK_RESPONSE);
          }
          else if(((value.pin/100) - 1) < countPeriferics)
          {
            USB.write(IO_OK_RESPONSE);
            data[0] = SET;
            data[1] = (byte)(value.pin%100);
            data[2] = (byte)(value.value & 0xFF);
            data[3] = (byte)((value.value & 0xFF00) >> 8);
            data[4] = (byte)((value.value & 0xFF0000) >> 16);
            xbee_send(periferics[(value.pin/100) - 1],data,3);
            flagAttendData++;
          }
          else
          {
            USB.write(IO_ERR_RESPONSE);
          }
        }
        
        oldTimeFlash = millis();
        stateFlash = 4;
      }
      else USB.write(IO_ERR_RESPONSE);

      contBuffer = 0;
    }
    else
    {
      if(contBuffer == BUFFER_DIM - 1 || buffer[0] == '\n') contBuffer = 0;
      else contBuffer++;
    }
  }

  if(millis() - oldAppeal > APPEAL_INTERVAL)
  {
    oldAppeal = millis();
    
    address[0] = 0x00;
    address[1] = 0x00;
    address[2] = 0x00;
    address[3] = 0x00;
    address[4] = 0x00;
    address[5] = 0x00;
    address[6] = 0xFF;
    address[7] = 0xFF;

    data[0] = (byte)IDY;
    xbee_send(address,data,1);
    flagAttendData++;
  }

  int num;
  if(flagAttendData > 0 && (num = xbee_receive(address,data,12)) > 0)
  {
    if(data[0] == '3')
    {
      int index;
      if((index = isPresentAt(periferics,countPeriferics,address)) < 0)
      {
        for(int count = 0; count < 8; count++)
        {
          periferics[countPeriferics][count] = data[count+1];
        }
        countPeriferics++;

        //updatePeriferics(periferics,countPeriferics);
      }
    }
    else if(data[0] == '2')
    {
      USB.write(data[1]);
    }

    flagAttendData--;
  }

  /*if(millis() - oldReadResetBut > RESET_BUT_INTERVAL)
  {
    oldReadResetBut = millis();

    if(digitalRead(RESET_BUT) == 0)
    {
      address[0] = 0x00;
      address[1] = 0x00;
      address[2] = 0x00;
      address[3] = 0x00;
      address[4] = 0x00;
      address[5] = 0x00;
      address[6] = 0xFF;
      address[7] = 0xFF;
  
      data[0] = (byte)RST;
      xbee_send(address,data,1);
    }
  }*/
}

int parseInput(char *buffer, struct changeValue* value)
{
  int flagErr = 0;

  char tipoOp[4]; tipoOp[3] = '\0';
  if(!flagErr) strncpy(tipoOp,buffer,3);

  if(!flagErr && strcmp(tipoOp,"GET") == 0) value->operation = 1;
  else if(!flagErr && strcmp(tipoOp,"SET") == 0) value->operation = 2;
  else flagErr = 1;

  short pin = 0;
  short count = 3+1;
  for(;!flagErr && buffer[count] != ';' && count < 6+1; count++)
  {
    flagErr = flagErr || buffer[count] < '0' || buffer[count] > '9';
    if(!flagErr) pin = pin * 10 + (buffer[count] - '0');
  }

  if(!flagErr) value->pin = pin;
  
  if(!flagErr && value->operation == 2)
  {
    //char val = buffer[count+1];
    long val = 0;

    for(count++;!flagErr && buffer[count] != '\0'; count++)
    {
      flagErr = flagErr || buffer[count] < '0' || buffer[count] > '9';
      if(!flagErr) val = val * 10 + (buffer[count] - '0');
    }
    
    if(!flagErr) value->value = val;
  }
  else value->value = -1;

  return -1 * flagErr;
}

int isPresentAt(byte addresses[][8], int countAddresses, byte *address)
{
  int isPresent = -1;
  
  for(short countA = 0; isPresent < 0 && countA < countAddresses; countA++)
  {
    byte isEq = 1;
    for(short countB = 0; isEq && countB < 8; countB++)
    {
      isEq = (addresses[countA][countB] == address[countB]);
    }

    if(isEq) isPresent = countA;
  }

  return isPresent;
}

void xbee_send(byte *recever, byte *data, int dim)
{
    SER.write((byte)0x7E);
    SER.write((byte)((14+dim)>>8));
    SER.write((byte)(14+dim));
    SER.write((byte)0x10);
    SER.write((byte)0x01);
    SER.write((byte)recever[0]);//indi
    SER.write((byte)recever[1]);
    SER.write((byte)recever[2]);
    SER.write((byte)recever[3]);
    SER.write((byte)recever[4]);
    SER.write((byte)recever[5]);
    SER.write((byte)recever[6]);
    SER.write((byte)recever[7]);//fine
    SER.write((byte)0xFF); 
    SER.write((byte)0xFE);  
    SER.write((byte)0x00);
    SER.write((byte)0x00);

    int checksum = (0x10)+(0x01)+recever[0]+recever[1]+recever[2]+recever[3]+recever[4]+recever[5]+recever[6]+recever[7]+(0xFF)+(0xFE);
    for(int cont = 0; cont < dim; cont++)
    {
      SER.write((byte)data[cont]);
      checksum += data[cont];
    }
    
    SER.write((byte)(0xFF-(checksum&0xFF)));
    SER.flush();
}

int xbee_receive(byte *sender, byte *data, int dim)
{
  byte car;
  int flagErr = 0;
  long oldTime = millis();

  while(!flagErr && SER.available() < 1) flagErr = (millis() - oldTime > 100);
  flagErr = flagErr || ((car = SER.read()) != 0x7E);

  oldTime = millis();
  while(!flagErr && SER.available() < 2) flagErr = (millis() - oldTime > 100);
  int lenFrame = -1;
  if(!flagErr) lenFrame = (int)((SER.read() << 8) + SER.read());

  oldTime = millis();
  while(!flagErr && SER.available() < lenFrame) flagErr = (millis() - oldTime > 100);

  int sum = 0;
  int result = 0;
  for(int cont = 0; !flagErr && cont < lenFrame; cont++)
  {
    byte readByte = SER.read();
    sum += readByte;

    if(cont > 0 && cont < 9)
    {
      sender[cont-1] = readByte;
    }
    else if(cont >= 12)
    {
      data[result++] = readByte;
    }

    flagErr = (result > dim);
  }

  oldTime = millis();
  while(!flagErr && SER.available() < 1) flagErr = (millis() - oldTime > 100);
  int checksum = SER.read();
  
  flagErr = flagErr || (0xFF  - (sum&0xFF)) != checksum;
  
  return (flagErr)? -1:result;
}

short initPeriferics(byte addresses[][8],int max)
{
  int num = EEPROM.read(0);

  for(short count = 0; count < num * 8; count++)
  {
    addresses[count / 8][count % 8] = EEPROM.read(count+1);
  }
}

void updatePeriferics(byte addresses[][8],int max)
{
  EEPROM.update(0,max);

  for(short count = 0; count < max * 8; count++)
  {
    EEPROM.update(count+1,addresses[count / 8][count % 8]);
  }
}

void erasePeriferics()
{
  EEPROM.update(0,0);
}
