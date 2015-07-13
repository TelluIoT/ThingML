#!/bin/bash
#
# Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
#
# Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# 	http://www.gnu.org/licenses/lgpl-3.0.txt
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


if [ $# -ne 2 ]
then
  echo "Usage : ./resultTxtToCSV.sh [filePath.txt] [filePathToCSV.csv]"
  echo "[filePathToCSV.csv] will be clear so be carrefull to put an empty file"
  echo "[filePath.txt] should create with command : top -d 1 > [filePath.txt]"
  exit
fi

echo "PID;%CPU;%MEM;TIME" | tee $2
cat $1 | grep node | awk '{
  if( NF == 13)
  {
    split($1,b,"m");
    printf("%s;%s;%s;%s\n", b[3], $9 , $10,$11);
    
  } else {
    printf("%s;%s;%s;%s\n", $2, $10 , $11,$12);
  }

  }' | tee -a $2
exit $?

