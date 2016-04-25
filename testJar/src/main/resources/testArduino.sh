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


echo "TestArdunio ($PWD)"
ano upload
echo "Coucou"
screen -c ../../../../screenrc -d -m -L -S arduino /dev/ttyACM0 9600 &
sleep $TIMEOUT
screen -X -S arduino quit
sleep 1
cat output.log

