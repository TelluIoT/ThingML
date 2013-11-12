#!/bin/bash

##################################################################
# GENERATED FROM THINGML (http://www.thingml.org) (DO NOT EDIT)
##################################################################

# This script installs and compile the ROS packages <ROS_PACKAGE> generated from ThingML.
# ROS is assumed to be properly installed and the ROS environment variables need to be set.
# Tested with ROS Electric version under Ununtu linux.

# Name of the ROS package to create
PACKAGE="<PACKAGE>"
PACKAGESUBPATH="<PACKAGESUBPATH>"

# Directory in which the code was generated
BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "---------------------------------------------------------"
echo "| Install ROS Package : $PACKAGE"
echo "---------------------------------------------------------"
echo "| Source Directory : $BASEDIR"
echo "|    ROS Workspace : $ROS_WORKSPACE"
echo "---------------------------------------------------------"

echo ""
echo "---------------------------------------------------------"
echo "[STEP 1]: Create ROS package $PACKAGE ..."
echo "---------------------------------------------------------"

cd $ROS_WORKSPACE/$PACKAGESUBPATH
# Remove existing package if it exists
rm -rf $PACKAGE
roscreate-pkg $PACKAGE std_msgs rospy roscpp
PACKAGEDIR=$ROS_WORKSPACE/$PACKAGESUBPATH/$PACKAGE

echo ""
echo "---------------------------------------------------------"
echo "[STEP 2]: Copy source file to ROS package ( $PACKAGEDIR )"
echo "---------------------------------------------------------"
set -x
cd $BASEDIR
cp -r src $PACKAGEDIR
cp -r msg $PACKAGEDIR
cat CMakeLists.txt >> $PACKAGEDIR/CMakeLists.txt
set +x
echo ""
echo "---------------------------------------------------------"
echo "[STEP 3]: Compile ROS package ( $PACKAGEDIR )"
echo "---------------------------------------------------------"
#rosmake $PACKAGE
cd $PACKAGEDIR
cmake .
make

if [ $? -eq 0 ] ; then
echo ""
echo "---------------------------------------------------------"
echo "                    BUILD SUCCESS"
echo " Start ROS Node with : rosrun $PACKAGE $PACKAGE"
echo "---------------------------------------------------------"
exit 0
fi

echo ""
echo "---------------------------------------------------------"
echo "                    BUILD FAILED"
echo "               Check for errors above."
echo "---------------------------------------------------------"
exit 1

# End of generated script
