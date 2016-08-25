#!/bin/bash
socat -x -v PTY,link=modem0 PTY,link=modem1

(timout 5 ./ClientEmptyCfg/ClientEmptyCfg_Cfg >> cliStdo.log 2>> cliStdr.log)&
(timout 5 ./ServerEmptyCfg/ServerEmptyCfg_Cfg >> srvStdo.log 2>> srvStdr.log)&

sleep 6

echo "Cli stdo:\n\n" >> testEmptyPosixPosix.log
cat cliStdo.log >> testEmptyPosixPosix.log
echo "\nCli stdr:\n\n" >> testEmptyPosixPosix.log
cat cliStdr.log >> testEmptyPosixPosix.log

echo "\n\nSrv stdo:\n\n" >> testEmptyPosixPosix.log
cat srvStdo.log >> testEmptyPosixPosix.log
echo "\nSrv stdr:\n\n" >> testEmptyPosixPosix.log
cat srvStdr.log >> testEmptyPosixPosix.log
