#!/bin/bash

if [ $# -ne 2 ]
then
  echo "Usage : ./resultTxtToCSV.sh [filePath.txt] [filePathToCSV.csv]"
  echo "[filePathToCSV.csv] will be clear so be carrefull to put an empty file"
  exit
fi

echo "PID;%CPU,%MEM,TIME" | tee $2
cat $1 | grep node | awk '{ printf("%s;%s;%s;%s\n", $2, $10, $11,$12); }' | tee -a $2
exit $?

