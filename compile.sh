#!/bin/bash

dir=$PWD/$(dirname $0)
flagErr=0

echo "Compilazione eseguibile server"

gcc "$dir/configAutomation.c" "$dir/configAutomation.h" "$dir/configDefines.h" "$dir/configDevices.c" "$dir/configDevices.h" "$dir/hardwareDefines.h" "$dir/io.c" "$dir/io.h" "$dir/rs232.c" "$dir/rs232.h" "$dir/serverAutomation.c" "$dir/server.c" -o "$dir/server"

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

oldDir=$PWD
cd $dir

./logRotate.sh $logFile

sleep 10

date > $logFile

if [ -c $usb -a -w $usb -a -r $usb ]
then
	$dir/./server 4000 $dir/devices $usb >> $logFile 2>&1 &
else
	$dir/./server 4000 $dir/devices >> $logFile 2>&1 &
fi

cd $oldDir' > $dir/starter.sh

chmod u+x $dir/logRotate.sh
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

gcc "$dir/client.c" -o "$dir/client"

if [ $? -eq 0 ]
then
    echo "Fatto"
else
    echo "Errore"
    exit 4
fi

