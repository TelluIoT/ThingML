#!/bin/sh

#RUN

#perf record
PROFILER_FREQ=$(cat /.profiler)
perf record -e cpu-clock -F $PROFILER_FREQ -p $PID -a -g -o /data/perf.data &

wait

#EXTRA

chmod -R 777 /data
