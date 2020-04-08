#!/bin/bash

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

cd $oldDir
