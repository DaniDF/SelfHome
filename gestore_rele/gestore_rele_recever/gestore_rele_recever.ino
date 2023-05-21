#define DEBUG 0

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

#define RST '0';
#define SET '1';
#define GET '2';
#define IDY '3';

#define SER Serial
#define LED 13

void setup() {
  pinMode(LED,OUTPUT);
  for(int cont = 2; cont < 13; cont++) { pinMode(cont,OUTPUT); digitalWrite(cont,LOW); }
  SER.begin(9600);
}

void loop() {
  static byte address[8] = {0};
  static byte data[12] = {0};
  
  int num = 0;
  if((num = xbee_receive(address,data,8)) > 0)
  {
    if(data[0] == '3')
    {
      data[1] = 0x00;
      data[2] = 0x13;
      data[3] = 0xA2;
      data[4] = 0x00;
      data[5] = 0x40;
      data[6] = 0xF1;
      data[7] = 0x6E;
      data[8] = 0x49;
      
      xbee_send(address,data,9);
    }
    else if(data[0] == '2')
    {
      pinMode(data[1],INPUT);
      data[1] = (byte)(!digitalRead(data[1]));
      pinMode(data[1],OUTPUT);
      xbee_send(address,data,2);
    }
    else if(data[0] == '1')
    {
      if(data[2] != 0 || data[2] != 1) {
        analogWrite(data[1],data[2]);
      }
      else {
        digitalWrite(data[1],data[2]);
      }

      data[1] = IO_OK_RESPONSE;
      xbee_send(address,data,2);
    }
    else if(data[0] == '0')
    {
      void (*reset)(void) = 0;
      reset();
    }
  }
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
