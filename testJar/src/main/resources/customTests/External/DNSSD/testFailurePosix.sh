#!/bin/bash

rm cliStdo.log &> /dev/null
rm avahi_stop.log &> /dev/null
rm avahi_start.log &> /dev/null

#TODO: timeouts are terrible, takes to much time to execute and does not guarantee that is been done before time is up

timeout 3 echo $PASSWORD | sudo -S service avahi-daemon stop > avahi_stop.log 2>&1&
sleep 4

timeout -s SIGINT 30 ./Failure/Failure > cliStdo.log 2> cliStdr.log&
sleep 32

#printf "Cli stdo:\n\n"
cat cliStdo.log
#printf "\nCli stdr:\n\n"
>&2 cat cliStdr.log

timeout 3 echo $PASSWORD | sudo -S service avahi-daemon start > avahi_start.log 2>&1&
sleep 4