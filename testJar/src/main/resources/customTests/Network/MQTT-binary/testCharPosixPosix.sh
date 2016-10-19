#!/bin/bash

sleep 1
timeout -s SIGINT 6 ./ServerCharCfg/ServerCharCfg > srvStdo.log 2> srvStdr.log&
sleep 1
timeout -s SIGINT 5 ./ClientCharCfg/ClientCharCfg > cliStdo.log 2> cliStdr.log&

sleep 6

printf "Cli stdo:\n\n"
cat cliStdo.log
printf "\nCli stdr:\n\n"
cat cliStdr.log

printf "\n\nSrv stdo:\n\n"
cat srvStdo.log
printf "\nSrv stdr:\n\n"
cat srvStdr.log
