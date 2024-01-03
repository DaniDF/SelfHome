FROM ubuntu:22.04
RUN mkdir /SelfHome
WORKDIR /SelfHome
COPY Core /SelfHome/Core
COPY Middle /SelfHome/Middle
COPY starter /SelfHome/starter
RUN apt-get update ; apt-get install gcc default-jre default-jdk -y
RUN cd /SelfHome/Core ; gcc configDevices.* hardwareDefines.h io.* rs232.* server.c -o server
EXPOSE 4000
CMD ["sh", "starter"]
