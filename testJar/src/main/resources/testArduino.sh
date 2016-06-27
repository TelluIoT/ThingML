#!/bin/bash
#
# Description:
#     This script is meant to compile ThingML test file specifying expected
#  input and output with @test annotations. The generated ThingML files with
#  configuration are then compiled to arduino one by one and uploaded to an
#  Arduino board connected as the `ttyACM0` device.
#
# Author:
# - Alexandre RIO <contact@alexrio.fr>
#
# Dependencies:
# - ano: https://github.com/scottdarch/Arturo
# - screen: classic one, from GNU
# - compilerThingML.sh: wrapping the ThingML compiler
#
############################################################################

TIMEOUT=5


echo "TestArduino ($PWD)"
ano upload -q
echo "" > output.log
echo "logfile ./output.log" > screenrc
serial_port=`ls /dev/ttyACM* | head -1`
screen -c screenrc -d -m -L -S arduino $serial_port 115200 &
sleep $TIMEOUT
screen -X -S arduino quit
sleep 1
cat output.log

