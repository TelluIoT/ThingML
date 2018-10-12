#!/bin/bash

# Get the latest version of the thingml compiler (snapshot!!!).
# TODO: We should really use a released version to avoid issues

# Abort and exit if any command fails
set -e

###########################################################
# Download the thingML compiler if any newer version is available
###########################################################
wget -N http://thingml.org/dist/ThingML2CLI.jar

# Generate the MQTT serialization adapter
java -jar ThingML2CLI.jar -t posixmqttjson -s posixmqttapi.thingml

echo "[THINGML: SUCCESS]"
