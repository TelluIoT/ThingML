#!/bin/sh

#RUN

#perf record
perf record -e cpu-clock -F 99 -p $PID -a -g -o /data/perf.data &

wait

#EXTRA

chmod -R 777 /data
