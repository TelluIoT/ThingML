#!/bin/bash

nametmp="test$1"
name=$nametmp"NodeJSPosix"
prop="$name.properties"
psh="$name.sh"

printf "Creating files $prop and $psh\n"


cp testEmptyNodeJSPosix.properties $prop
cp testEmptyNodeJSPosix.sh $psh

sub="s/Empty/$1/g"

sed -i $sub $prop
sed -i $sub $psh

sed -i '/oracle/c\'"$(grep "oracle" test$1""PosixPosix.properties)" $name.properties
