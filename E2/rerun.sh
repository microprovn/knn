#!/bin/bash

re='^[0-9]+$' 
if [[ "$1" -lt "5"  ||  "$1" -gt "20"  ]] 
then
	echo "Error: hold-out must between 5 and 20"
	exit 1
fi

if [[ ($2 < 0) || !($2 =~ $re) ]] 
then 
	echo "Error: Invalid K"
	exit 1
fi

if [[ "$3" != "cosine" && "$3" != "euclidean" ]]
then 
	echo "Error: Distance is neither cosine or euclidean"
	exit 1
fi

if [[ ! -f $4 ]]
then 
	echo "Error: input file does not exist"
	exit 1
fi

if [[ -f $5 ]]
then 
	echo "Error: output file exist"
	exit 1
fi

java -jar target/knn.jar $1 $2 $3 $4 > $5
