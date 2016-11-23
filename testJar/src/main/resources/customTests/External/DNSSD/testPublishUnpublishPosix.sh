#!/bin/bash

rm cliStdo.log &> /dev/null

#TODO: timeouts are terrible, takes to much time to execute and does not guarantee that is been done before time is up

timeout -s SIGINT 30 ./PublishUnpublish/PublishUnpublish > cliStdo.log 2> cliStdr.log&

sleep 32

#printf "Cli stdo:\n\n"
cat cliStdo.log
#printf "\nCli stdr:\n\n"
>&2 cat cliStdr.log