#define USB Serial
#define SER Serial1

void setup() {
  USB.begin(115200);
  SER.begin(115200);
}

void loop() {
  if(SER.available())
  {
    USB.write(SER.read());
  }

}
