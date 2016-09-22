#!/bin/bash

nametmp="test$1"
name=$nametmp"NodeJSNodeJS"
prop="$name.properties"
psh="$name.sh"

printf "Creating files $prop and $psh\n"


cp testEmptyNodeJSNodeJS.properties $prop
cp testEmptyNodeJSNodeJS.sh $psh

sub="s/Empty/$1/g"

sed -i $sub $prop
sed -i $sub $psh

sed -i '/oracle/c\'"$(grep "oracle" test$1""PosixPosix.properties)" $name.properties
