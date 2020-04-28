#!/bin/bash

if [ $# -ne 1 ]
then
	echo Errore necessario un file come parametro
	exit 1
fi

dir=$(dirname $1)
bas=$(basename $1)
lenBas=$(echo $bas | wc -c)

maxIndex=$(ls $dir | egrep "$bas[1-9]?[0-9]+$" | wc -l)

for i in $(seq $maxIndex -1 1)
do
	file=$bas$i

	if [ -f $dir/$file ]
	then
		index=$(echo $file | cut -c$lenBas-)
		index=$(( $index + 1 ))
		mv $dir/$file $dir/$bas$index
	fi
done

mv $dir/$bas $dir/$bas"1"
