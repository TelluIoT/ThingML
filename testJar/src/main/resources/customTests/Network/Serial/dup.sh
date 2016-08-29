#!/bin/bash

nametmp="test$1"
name=$nametmp"PosixPosix"
prop="$name.properties"
psh="$name.sh"

printf "Creating files $prop and $psh\n"


cp testEmptyPosixPosix.properties $prop
cp testEmptyPosixPosix.sh $psh

sub="s/Empty/$1/g"

sed -i $sub $prop
sed -i $sub $psh
