#!/bin/bash
while read line
do
    name=$line
    echo "downloading from file - $name"
    
    [[ "$name" =~ [^=]+$ ]] 
    filename=${BASH_REMATCH[0]}

    wget -O zip_down/$filename $name
done < $1