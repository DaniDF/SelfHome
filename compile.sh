#!/bin/bash

dir=$PWD/$(dirname $0)
flagErr=0

echo "Compilazione eseguibile server"

gcc configAutomation.c configAutomation.h configDefines.h configDevices.c configDevices.h hardwareDefines.h io.c io.h rs232.c rs232.h serverAutomation.c server.c -o server

if [ $? -eq 0 ]
then
    echo "Fatto"
else
    echo "Errore"
    exit 1
fi

#Generare files per systemctl

deviceUsb="/dev/ttyACM0"
echo '#!/bin/bash

dir="/home/dani/Desktop/SelfHome"
usb="/dev/ttyACM0"
logFile="/var/log/selfhome"

sleep 10

date > $logFile

if [ -c $usb -a -w $usb -a -r $usb ]
then
	$dir/./server 4000 $dir/devices $usb >> $logFile 2>&1 &
else
	$dir/./server 4000 $dir/devices >> $logFile 2>&1 &
fi' > $dir/starter.sh

chmod u+x $dir/starter.sh

fileSys="/etc/init.d/selfhome"

echo '#!/bin/bash
### BEGIN INIT INFO
# Provides:          selfhome
# Required-Start:    hal
# After:             network.target
# Required-Stop:     
# Should-Start:      
# Should-Stop:       
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Server SelfHome
# Description:       Activate the home autamation server
### END INIT INFO


if [ $1 == stop ]
then
	echo STOP selfHome
	kill -SIGINT $(ps haux | egrep '"'"'.*SelfHome.*$'"'"' | tr -s '"'"' '"'"' | cut -d'"'"' '"'"' -f2 | sort -n | head -1)
else
	'$dir'/./starter.sh&
fi' > $fileSys

if [ $? -ne 0 ]
then
    echo Errore scrittura file di sistema
    exit 2
fi

chmod +x $fileSys

if [ $? -ne 0 ]
then
    echo Errore permessi file di sistema
    exit 2
fi

#Installare server con systemctl

systemctl enable selfhome

if [ $? -ne 0 ]
then
    echo Errore installazione avvio automatico
    exit 3
fi

systemctl start selfhome

if [ $? -ne 0 ]
then
    echo Errore avvio
fi

echo "Compilazione eseguibile client"

gcc client.c -o client

if [ $? -eq 0 ]
then
    echo "Fatto"
else
    echo "Errore"
    exit 4
fi

