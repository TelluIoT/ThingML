#!/bin/bash

sleep 1
timeout -s SIGINT 6 ./ServerUIntCfg/ServerUIntCfg > srvStdo.log 2> srvStdr.log&
sleep 1
timeout -s SIGINT 5 ./ClientDebugSPCfg/ClientDebugSPCfg > cliStdo.log 2> cliStdr.log&

sleep 6

#printf "Cli stdo:\n\n"
cat cliStdo.log
#printf "\nCli stdr:\n\n"
>&2 cat cliStdr.log

#printf "\n\nSrv stdo:\n\n"
>&2 cat srvStdo.log
#printf "\nSrv stdr:\n\n"
>&2 cat srvStdr.log
